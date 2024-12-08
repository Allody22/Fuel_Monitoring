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
public class AddGasStationAddressRequest {

    @Schema(name = "Имя конкретного филиала АЗС")
    @NotNull(message = "Пожалуйста, введите имя конкретного филиала АЗС")
    @NotBlank(message = "Пожалуйста, введите имя конкретного филиала АЗС")
    private String name;

    @Schema(name = "Рейтинг конкретного филиала АЗС в формате double")
    private Double rating;

    @Schema(name = "Адрес конкретного филиала АЗС")
    @NotNull(message = "Пожалуйста, введите адрес конкретного филиала АЗС")
    @NotBlank(message = "Пожалуйста, введите адрес конкретного филиала АЗС")
    private String address;

    @Schema(name = "Количество отзывов на конкретный филиал АЗС")
    private Integer feedbacks;

}
