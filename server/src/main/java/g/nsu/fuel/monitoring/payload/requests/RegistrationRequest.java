package g.nsu.fuel.monitoring.payload.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос для регистрации нового пользователя")
public class RegistrationRequest {

    @Schema(description = "Номер телефона пользователя в любом формате", example = "69365810606")
    @NotNull(message = "Пожалуйста, введите номер телефона")
    @NotBlank(message = "Пожалуйста, введите номер телефона")
    private String phoneNumber;

    @Schema(description = "Пароль данного пользователя", example = "very_strong_password")
    @NotNull(message = "Пожалуйста, введите номер пароль")
    @NotBlank(message = "Пожалуйста, введите номер пароль")
    private String password;

    @Schema(description = "Фингерпринт пользователя.", example = "hello world i made it", required = true)
    @NotNull(message = "Пожалуйста, введите фингерпринт")
    @NotBlank(message = "Пожалуйста, введите фингерпринт")
    private String fingerprint;
}
