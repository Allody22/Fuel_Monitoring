package g.nsu.fuel.monitoring.services;

import g.nsu.fuel.monitoring.entities.oil.GasStation;
import g.nsu.fuel.monitoring.entities.oil.GasStationByAddress;
import g.nsu.fuel.monitoring.model.exception.NotInDataBaseException;
import g.nsu.fuel.monitoring.payload.requests.AddGasStationAddressRequest;
import g.nsu.fuel.monitoring.payload.response.GasStationAddressSummary;
import g.nsu.fuel.monitoring.repository.GasStationByAddressRepository;
import g.nsu.fuel.monitoring.repository.GasStationRepository;
import g.nsu.fuel.monitoring.repository.jdbc.GasStationAddressJdbcRepository;
import g.nsu.fuel.monitoring.services.interfaces.GasStationAddressService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@AllArgsConstructor
public class GasStationAddressServiceImpl implements GasStationAddressService {

    private final GasStationAddressJdbcRepository gasStationAddressJdbcRepository;

    private final GasStationRepository gasStationRepository;

    private final GasStationByAddressRepository gasStationByAddressRepository;


    public void deleteGasStationAddressById(Long id) {
        if (!gasStationByAddressRepository.existsById(id)) {
            throw new NotInDataBaseException("Заправка по адресу с айди " + id);
        }
        gasStationByAddressRepository.deleteById(id);
    }

    public void addNewGasStationAddress(AddGasStationAddressRequest addGasStationAddressRequest) {
        String gasStationName = addGasStationAddressRequest.getName();
        GasStation gasStation = gasStationRepository.findByName(gasStationName)
                .orElseThrow(() -> new NotInDataBaseException("Заправка с именем " + gasStationName));
        GasStationByAddress gasStationByAddress = new GasStationByAddress();
        gasStationByAddress.setGasStation(gasStation);
        gasStationByAddress.setAddress(addGasStationAddressRequest.getAddress());
        gasStationByAddress.setFeedbacks(addGasStationAddressRequest.getFeedbacks());
        gasStationByAddress.setRating(addGasStationAddressRequest.getRating());
        gasStationByAddress.setUpdatedAt(LocalDate.now());
        gasStationByAddressRepository.save(gasStationByAddress);
    }

    public GasStationAddressSummary getGasStationAddressSummary(Long id, int interval) {
        if (!gasStationByAddressRepository.existsById(id)) {
            throw new NotInDataBaseException("Филиал АЗС с айди " + id);
        }

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(interval - 1);
        return gasStationAddressJdbcRepository.getSummary(id, startDate, endDate);
    }

}
