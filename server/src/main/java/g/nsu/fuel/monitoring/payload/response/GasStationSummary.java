package g.nsu.fuel.monitoring.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class GasStationSummary {

    private Long id;

    private Integer numberOfAddresses;

    private List<OilTypeAverageWidgets> widgets;

    private List<OilTypePriceData> data;

}
