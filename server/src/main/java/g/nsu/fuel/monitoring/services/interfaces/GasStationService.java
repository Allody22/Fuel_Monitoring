package g.nsu.fuel.monitoring.services.interfaces;

import g.nsu.fuel.monitoring.payload.requests.AddGasStationRequest;
import g.nsu.fuel.monitoring.payload.response.GasStationResponse;
import g.nsu.fuel.monitoring.payload.response.GasStationSummary;

import java.util.List;

public interface GasStationService {

    List<GasStationResponse> getAllGasStations();

    GasStationSummary getGasStationSummary(Long id, Integer interval);

    void addNewGasStation(AddGasStationRequest addGasStationRequest);

    void deleteGasStationById(Long id);

}
