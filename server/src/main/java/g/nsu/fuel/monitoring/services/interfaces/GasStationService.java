package g.nsu.fuel.monitoring.services.interfaces;

import g.nsu.fuel.monitoring.payload.requests.AddGasStationAddressRequest;
import g.nsu.fuel.monitoring.payload.requests.AddGasStationRequest;
import g.nsu.fuel.monitoring.payload.response.GasStationResponse;

import java.util.List;

public interface GasStationService {

    List<GasStationResponse> getAllGasStations();

    void addNewGasStation(AddGasStationRequest addGasStationRequest);

    void addNewGasStationAddress(AddGasStationAddressRequest addGasStationAddressRequest);

    void deleteGasStationById(Long id);

    void deleteGasStationAddressById(Long id);

}
