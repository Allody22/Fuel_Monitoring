package g.nsu.fuel.monitoring.services.interfaces;

import g.nsu.fuel.monitoring.payload.response.OilPriceResponse;

import java.util.List;

public interface OilService {

    OilPriceResponse getOilPriceByType(String oilType);

    List<String> getAllOilTypes();

}
