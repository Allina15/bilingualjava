package bilingual.dto.response.auth;

import bilingual.entity.enums.Role;
import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String email,
        String token,
        Role role
) {
}