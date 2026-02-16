package models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

public record UpdateOrderRequest(

        @Schema(description = "Requester ID", example = "698c8728c537bc0c968737b4")
        @Size(min = 24, max = 36, message = "The requesterId must be between 24 and 36 characters long")
        String requesterId,

        @Schema(description = "Customer ID", example = "698c8728c537bc0c968737b4")
        @Size(min = 24, max = 36, message = "The customerId must be between 24 and 36 characters long")
        String customerId,

        @Schema(description = "Order title", example = "Fix my computer")
        @Size(min = 3, max = 45, message = "The title must be between 3 and 45 characters long")
        String title,

        @Schema(description = "Order description", example = "My computer is broken")
        @Size(min = 10, max = 3000, message = "The description must be between 10 and 3000 characters long")
        String description,

        @Schema(description = "Order status", example = "Open")
        @Size(min = 4, max = 15, message = "The status must be between 4 and 15 characters long")
        String status
) { }
