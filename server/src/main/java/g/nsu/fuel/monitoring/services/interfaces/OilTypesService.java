package g.nsu.fuel.monitoring.services.interfaces;

import g.nsu.fuel.monitoring.payload.response.OilTypeResponse;

import java.util.List;

public interface OilTypesService {

    List<OilTypeResponse> getAllOilTypes();

}
