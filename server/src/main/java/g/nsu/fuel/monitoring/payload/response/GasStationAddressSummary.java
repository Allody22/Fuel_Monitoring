package g.nsu.fuel.monitoring.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class GasStationAddressSummary {

    private List<OilTypeAverageWidgets> widgets;

    private List<OilTypePriceData> data;

}
