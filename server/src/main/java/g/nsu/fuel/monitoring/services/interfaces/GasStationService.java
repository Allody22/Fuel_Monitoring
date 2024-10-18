package g.nsu.fuel.monitoring.services.interfaces;

import g.nsu.fuel.monitoring.entities.oil.GasStation;

import java.util.List;

public interface GasStationService {

    List<GasStation> getStationsByOilType(String oilType);

}
