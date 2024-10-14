package g.nsu.fuel.monitoring.payload.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос для регистрации нового пользователя")
public class RegistrationRequest {

    @Schema(description = "Номер телефона пользователя в любом формате", example = "69365810606")
    private String phoneNumber;

    @Schema(description = "Пароль данного пользователя", example = "very_strong_password")
    private String password;
}
