package g.nsu.fuel.monitoring.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GasStationByAddressResponse {

    private Double ration;

    private String address;

    private Integer feedbacks;

    private LocalDate updatedAt;
}
