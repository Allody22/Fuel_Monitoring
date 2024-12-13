package g.nsu.fuel.monitoring.payload.response;

import g.nsu.fuel.monitoring.model.enums.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountInfoResponse {

    @Schema(description = "Айди аккаунта" )
    private Long id;

    @Schema(description = "Номер телефона пользователя" )
    private String phoneNumber;

    @Schema(description = "Используемый пользователем тип топлива")
    private String oilType;

    @Schema(description = "Список ролей пользователя", enumAsRef = true)
    private List<RoleEnum> roles;
}
