package g.nsu.fuel.monitoring.services.interfaces;

import g.nsu.fuel.monitoring.payload.response.OilPriceResponse;


public interface OilService {

    OilPriceResponse getOilPriceByType(String oilType);

}
