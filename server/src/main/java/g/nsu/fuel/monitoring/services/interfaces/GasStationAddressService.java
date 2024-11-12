package g.nsu.fuel.monitoring.services.interfaces;

import g.nsu.fuel.monitoring.payload.requests.AddGasStationAddressRequest;
import g.nsu.fuel.monitoring.payload.requests.AddGasStationRequest;
import g.nsu.fuel.monitoring.payload.response.GasStationAddressSummary;
import g.nsu.fuel.monitoring.payload.response.GasStationResponse;

import java.util.List;

public interface GasStationAddressService {

    void addNewGasStationAddress(AddGasStationAddressRequest addGasStationAddressRequest);

    void deleteGasStationAddressById(Long id);

    GasStationAddressSummary getGasStationAddressSummary(Long id, int interval);
}
