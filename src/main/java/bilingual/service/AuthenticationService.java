package bilingual.service;

import bilingual.dto.request.auth.*;
import bilingual.dto.response.auth.AuthenticationResponse;
import bilingual.dto.response.auth.TokenResponse;
import com.google.firebase.auth.FirebaseAuthException;

public interface AuthenticationService {
    AuthenticationResponse signIn(SignInRequest request);
    AuthenticationResponse signUp(SignUpRequest signUpRequest);
    void initiatePasswordReset(InitiatePassword initiatePassword);
    TokenResponse verifyPasswordReset(VerifyPassword password);
    void setPassword(String uniqueIdentifier,  ForgotPassword password);
    AuthenticationResponse authWithGoogleAccount(String tokenId) throws FirebaseAuthException;
}
