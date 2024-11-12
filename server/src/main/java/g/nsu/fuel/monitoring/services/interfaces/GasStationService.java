package g.nsu.fuel.monitoring.services.interfaces;

import g.nsu.fuel.monitoring.payload.requests.AddGasStationRequest;
import g.nsu.fuel.monitoring.payload.response.GasStationResponse;

import java.util.List;

public interface GasStationService {

    List<GasStationResponse> getAllGasStations();

    void addNewGasStation(AddGasStationRequest addGasStationRequest);

    void deleteGasStationById(Long id);

}
