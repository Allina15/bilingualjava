package bilingual.dto.response.auth;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class TokenResponse {

    private HttpStatus httpStatus;
    private String token;
}
