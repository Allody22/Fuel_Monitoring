package g.nsu.fuel.monitoring.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GasStationByAddressResponse {

    @Schema(description = "Айди филиала АЗС")
    private Long id;

    @Schema(description = "Рейтинг определённого филиала АЗС")
    private Double rating;

    @Schema(description = "Адрес определённого филиала АЗС")
    private String address;

    @Schema(description = "Количество отзывов на определённый филиал АЗС")
    private Integer feedbacks;

    @Schema(description = "Дата последнего обновления информации об этом филиале АЗС")
    private LocalDate updatedAt;
}
