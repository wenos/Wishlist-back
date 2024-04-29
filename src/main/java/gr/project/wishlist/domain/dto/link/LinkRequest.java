package gr.project.wishlist.domain.dto.link;


import gr.project.wishlist.domain.model.AccessMode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Запрос для создания ссылки")
public class LinkRequest {
        @Schema(description = "Id вишлиста")
        private Long wishlistId;

        @Schema(description = "Режим доступа")
        private AccessMode accessMode;

        public LinkRequest(Long wishlistId, AccessMode accessMode) {
                this.wishlistId = wishlistId;
                this.accessMode = (accessMode != null) ? accessMode : AccessMode.VIEW_MODE;
        }
        public LinkRequest() {
                this.accessMode = AccessMode.VIEW_MODE;
        }
        // геттеры и сеттеры для wishlistId и accessMode
}