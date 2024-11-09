package g.nsu.fuel.monitoring.payload.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddGasStationAddressRequest {

    private String name;

    private Double rating;

    private String address;

    private Integer feedbacks;

}
