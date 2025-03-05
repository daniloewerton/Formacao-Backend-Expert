package models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.With;
import models.enums.ProfileEnum;

import java.util.Set;

@With
public record CreateUserRequest(
        @Schema(description = "User name", example = "Danilo Ewerton")
        @NotBlank(message = "Name cannot be empty")
        @Size(min = 3, max = 50, message = "Name must contain between 3 and 50 characters")
        String name,

        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Invalid email")
        @Schema(description = "User email", example = "danilo@gmail.com")
        @Size(min = 6, max = 50, message = "Email must contain between 6 and 50 characters")
        String email,

        @NotBlank(message = "Password cannot be empty")
        @Schema(description = "User password", example = "123456")
        @Size(min = 6, max = 50, message = "Password must contain between 6 and 50 characters")
        String password,

        @Schema(description = "User password", example = "[\"ROLE_ADMIN\", \"ROLE_CUSTOMER\"]")
        Set<ProfileEnum> profiles
) { }
