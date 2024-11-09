package g.nsu.fuel.monitoring.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GasStationResponse {

    private String name;

    private String siteURL;

    private String email;

    private String type;

    private List<GasStationByAddressResponse> gasStations;
}
