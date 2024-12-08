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
public class AddGasStationRequest {

    @Schema(name = "Название сети АЗС")
    @NotNull(message = "Пожалуйста, введите название новой сети АЗС")
    @NotBlank(message = "Пожалуйста, введите название новой сети АЗС")
    private String name;

    @Schema(name = "Адрес сайта сети АЗС, будет использоваться для сбора информации о ценах на топливо.")
    private String siteURL;

    @Schema(name = "Контактная почта сети АЗС")
    private String email;

    @Schema(name = "Тип сети АЗС, по факту можно передавать что угодно")
    private String type;
}
