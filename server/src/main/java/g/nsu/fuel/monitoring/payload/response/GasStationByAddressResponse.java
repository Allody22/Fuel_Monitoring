package g.nsu.fuel.monitoring.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GasStationByAddressResponse {

    private Long id;

    private Double rating;

    private String address;

    private Integer feedbacks;

    private LocalDate updatedAt;
}
