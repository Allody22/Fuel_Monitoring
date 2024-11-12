package g.nsu.fuel.monitoring.payload.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddGasStationRequest {

    private String name;

    private String siteURL;

    private String email;

    private String type;
}
