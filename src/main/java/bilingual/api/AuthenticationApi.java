package bilingual.api;

import bilingual.dto.request.auth.SignInRequest;
import bilingual.dto.request.auth.SignUpRequest;
import bilingual.dto.response.auth.AuthenticationResponse;
import bilingual.service.AuthenticationService;
import bilingual.dto.request.auth.VerifyPassword;
import bilingual.dto.request.auth.ForgotPassword;
import bilingual.dto.request.auth.InitiatePassword;
import com.google.firebase.auth.FirebaseAuthException;
import bilingual.dto.response.auth.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentication API", description = "APIs for managing authentication")
@CrossOrigin
public class AuthenticationApi {

    private final AuthenticationService authService;

    @PostMapping("/signUp")
    @Operation(summary = "Sign up method")
    AuthenticationResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        return authService.signUp(signUpRequest);
    }

    @PostMapping("/signIn")
    @Operation(summary = "Sign in method")
    AuthenticationResponse signIn(@RequestBody @Valid SignInRequest signInRequest) {
        return authService.signIn(signInRequest);
    }

    @PostMapping("/initiate")
    @Operation(summary = "Send verification code to user email")
    public ResponseEntity<String> initiatePasswordReset(@RequestBody @Valid InitiatePassword initiatePassword) {
        authService.initiatePasswordReset(initiatePassword);
        return ResponseEntity.ok("Password reset initiated successfully. Check your email for instructions.");
    }

    @PostMapping("/verify")
    @Operation(summary = "Verify the verification code")
    public TokenResponse verifyPasswordReset(@RequestBody @Valid VerifyPassword password) {
        return authService.verifyPasswordReset(password);
    }

    @PostMapping("/setPassword")
    @Operation(summary = "Save new user password")
    public ResponseEntity<String> setPassword(@RequestParam String uniqueIdentifier,
                                              @RequestBody @Valid ForgotPassword password){
        authService.setPassword(uniqueIdentifier, password);
        return ResponseEntity.ok("Password reset successfully. You can now login with your new password.");
    }

    @PostMapping("/authenticate/google")
    @Operation(summary = "Method to sign up with google")
    public AuthenticationResponse authWithGoogleAccount(@RequestParam String tokenId) throws FirebaseAuthException {
        return authService.authWithGoogleAccount(tokenId);
    }
}