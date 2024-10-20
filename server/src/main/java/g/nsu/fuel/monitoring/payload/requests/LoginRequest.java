package g.nsu.fuel.monitoring.payload.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequest {

    @Schema(description = "Почта пользователя.", example = "admin@university.com", required = true)
    private String username;

    @Schema(description = "Пароль пользователя.", example = "test", required = true)
    private String password;

    @Schema(description = "Фингерпринт пользователя.", example = "hello world i made it", required = true)
    private String fingerprint;
}
