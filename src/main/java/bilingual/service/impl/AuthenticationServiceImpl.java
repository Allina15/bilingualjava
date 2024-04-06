package bilingual.service.impl;

import bilingual.dto.request.auth.SignInRequest;
import bilingual.dto.request.auth.SignUpRequest;
import bilingual.dto.response.auth.AuthenticationResponse;
import bilingual.dto.response.auth.TokenResponse;
import bilingual.emailSender.JavaMailService;
import bilingual.dto.request.auth.InitiatePassword;
import bilingual.dto.request.auth.ForgotPassword;
import bilingual.dto.request.auth.VerifyPassword;
import bilingual.entity.User;
import bilingual.entity.UserInfo;
import bilingual.entity.enums.Role;
import bilingual.exception.AlreadyExistException;
import bilingual.repo.UserInfoRepo;
import bilingual.repo.UserRepo;
import bilingual.security.JwtService;
import bilingual.service.AuthenticationService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.webjars.NotFoundException;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final UserInfoRepo userInfoRepo;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailService javaMailService;
    private final TemplateEngine templateEngine;

    @Override
    public AuthenticationResponse signUp(SignUpRequest request) {
        if (userInfoRepo.existsByEmail(request.email())) {
            String message = "User with email: " + request.email() + " is already exists";
            log.error(message);
            throw new AlreadyExistException(message);
        }
        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .build();
        userRepo.save(user);

        UserInfo userInfo = UserInfo.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .user(user)
                .build();

        user.setUserInfo(userInfo);
        userInfoRepo.save(userInfo);

        String token = jwtService.generateToken(userInfo);
        return AuthenticationResponse.builder()
                .token(token)
                .email(userInfo.getEmail())
                .role(userInfo.getRole())
                .build();
    }

    @Override
    public AuthenticationResponse signIn(SignInRequest request) {
        User user = userRepo.getUserByUserInfoEmail(request.email()).orElseThrow(() -> {
            String message = "User account with email: " + request.email() + " is not found";
            log.error(message);
            return new EntityNotFoundException(message);
        });

        if (!passwordEncoder.matches(request.password(), user.getUserInfo().getPassword())) {
            throw new BadCredentialsException("Wrong password please provide right credentials");
        }
        String jwtToken = jwtService.generateToken(user.getUserInfo());
        return AuthenticationResponse.builder().
                token(jwtToken)
                .email(user.getUserInfo().getEmail())
                .role(user.getUserInfo().getRole())
                .build();
    }

    @Override
    public void initiatePasswordReset(InitiatePassword initiatePassword) {
        String verificationCode = generateVerificationCode();
        storeVerificationCode(initiatePassword.getEmail(), verificationCode);
        sendVerificationCodeByEmail(initiatePassword.getEmail(), verificationCode);
    }

    @Override
    public TokenResponse verifyPasswordReset(VerifyPassword password) {
        UserInfo userInfo = userInfoRepo.getUserInfoByVerificationCode(password.getUniqueIdentifier()).orElseThrow(()-> {
            log.error("User account with identifier: " + password.getUniqueIdentifier() + " is not found");
            return new NotFoundException("User account is not found");
        });

        if (!password.getUniqueIdentifier().equals(userInfo.getVerificationCode())) {
            log.error("Invalid verification code.");
            throw new BadCredentialsException("Invalid verification code.");
        }

        if (LocalDateTime.now().isAfter(userInfo.getVerificationCodeExpiration())) {
            log.error("Verification code has expired.");
            throw new BadCredentialsException("Verification code has expired. TRY AGAIN");
        }

        return TokenResponse.builder()
                .httpStatus(HttpStatus.OK)
                .token(password.getUniqueIdentifier())
                .build();
    }

    @Override
    public void setPassword(String uniqueIdentifier,  ForgotPassword password) {
        UserInfo userInfo = userInfoRepo.getUserInfoByVerificationCode(uniqueIdentifier).orElseThrow(()-> {
            log.error("User account with identifier: " + uniqueIdentifier + " is not found");
            return new NotFoundException("User account is not found");
        });

        if (!password.getNewPassword().equals(password.getConfirmPassword())) {
            log.error("New password and confirm password do not match.");
            throw new IllegalArgumentException("New password and confirm password does not match.");
        }

        userInfo.setPassword(passwordEncoder.encode(password.getNewPassword()));
        userInfo.setVerificationCode(null);
        userInfo.setVerificationCodeExpiration(null);
        userInfoRepo.save(userInfo);
    }

    public void storeVerificationCode(String userEmail, String verificationCode) {
        UserInfo userInfo = userInfoRepo.findByEmail(userEmail).orElseThrow(()-> {
            String message = "User account with email: " + userEmail + " is not found";
            log.error(message);
            return new NotFoundException(message);
        });

        userInfo.setVerificationCode(verificationCode);
        userInfo.setVerificationCodeExpiration(LocalDateTime.now().plusMinutes(3));

        userInfoRepo.save(userInfo);
    }

    private void sendVerificationCodeByEmail(String userEmail, String verificationCode) {
        UserInfo userInfo = userInfoRepo.findByEmail(userEmail).orElseThrow(()-> {
            String message = "User account with email: " + userEmail + " is not found";
            log.error(message);
            return new NotFoundException("Email is not found");
        });

        String subject = "Password Reset Verification Code";
        Context context = new Context();
        context.setVariable("code", verificationCode);
        String htmlContent = templateEngine.process("verification_code.html", context);
        javaMailService.sendVerificationCodeHtml(userInfo.getEmail(), subject, htmlContent);

    }

    public static String generateVerificationCode() {
        return RandomStringUtils.randomAlphanumeric(6);
    }

    @Override
    public AuthenticationResponse authWithGoogleAccount(String tokenId) throws FirebaseAuthException {
        FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(tokenId);

        if (userRepo.existsByUserInfoEmail(firebaseToken.getEmail())) {
            log.info("user with email: " + firebaseToken.getEmail() + " is already exists");
            UserInfo userAc =userInfoRepo.getUserInfosByEmail(firebaseToken.getEmail());
            String accessToken = jwtService.generateToken(userAc);
            log.info("Authentication successful for email: {}", firebaseToken.getEmail());
            return AuthenticationResponse.builder()
                    .token(accessToken)
                    .email(firebaseToken.getEmail())
                    .role(userAc.getRole())
                    .build();
        }

        User newUser = new User();
        String[] name = firebaseToken.getName().split(" ");
        newUser.setFirstName(name[0]);
        newUser.setLastName(name[1]);
        userRepo.save(newUser);

        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(firebaseToken.getEmail());
        userInfo.setPassword(passwordEncoder.encode(firebaseToken.getEmail()));
        userInfo.setRole(Role.USER);
        newUser.setUserInfo(userInfo);
        userInfoRepo.save(userInfo);

        log.info("User created successfully for email: {}", firebaseToken.getEmail());
        UserInfo userInformation = newUser.getUserInfo();
        String accessToken = jwtService.generateToken(userInformation);
        log.info("Authentication successful for email: {}", firebaseToken.getEmail());
        return AuthenticationResponse.builder()
                .token(accessToken)
                .email(firebaseToken.getEmail())
                .role(userInformation.getRole())
                .build();

    }

    @PostConstruct
    void init() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource("oauth.json").getInputStream());
        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .build();
        FirebaseApp.initializeApp(firebaseOptions);
    }
}