package gr.project.wishlist.domain.dto.giftlist;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Запрос на создание списка")
public record WishlistRequest(
        @NotBlank(message = "Название списка не может быть пустым")
        @Schema(description = "Наименование списка")
        String title,
        @NotBlank(message = "Описание списка не может быть пустым")
        @Schema(description = "Описание списка")
        String description
) {
}
