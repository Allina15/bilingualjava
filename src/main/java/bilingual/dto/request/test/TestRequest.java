package bilingual.dto.request.test;

import jakarta.validation.constraints.NotBlank;

public record TestRequest(
        @NotBlank(message = "Test title must be provided")
        String title,
        @NotBlank(message = "Description must be provided")
        String shortDescription
) {
}
