package g.nsu.fuel.monitoring.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class GasStationAddressSummary {

    private List<OilTypeAverageWidgets> widgets;

    private List<OilTypePriceData> data;

}
