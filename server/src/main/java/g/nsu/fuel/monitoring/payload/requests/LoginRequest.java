package g.nsu.fuel.monitoring.payload.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {

    @Schema(description = "Имя пользователя.", example = "admin@university.com", required = true)
    @NotNull(message = "Пожалуйста, введите номер телефона")
    @NotBlank(message = "Пожалуйста, введите номер телефона")
    private String phoneNumber;

    @Schema(description = "Пароль пользователя.", example = "test", required = true)
    @NotNull(message = "Пожалуйста, введите номер пароль")
    @NotBlank(message = "Пожалуйста, введите номер пароль")
    private String password;

    @Schema(description = "Фингерпринт пользователя.", example = "hello world i made it", required = true)
    @NotNull(message = "Пожалуйста, введите фингерпринт")
    @NotBlank(message = "Пожалуйста, введите фингерпринт")
    private String fingerprint;
}
