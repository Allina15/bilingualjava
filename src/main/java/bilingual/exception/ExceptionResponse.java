package bilingual.exception;

import lombok.Builder;

@Builder
public record ExceptionResponse(
        String message

) {
}
