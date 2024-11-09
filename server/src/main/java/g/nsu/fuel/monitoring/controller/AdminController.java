package g.nsu.fuel.monitoring.controller;

import g.nsu.fuel.monitoring.payload.requests.AddGasStationAddressRequest;
import g.nsu.fuel.monitoring.payload.requests.AddGasStationRequest;
import g.nsu.fuel.monitoring.payload.response.DataResponse;
import g.nsu.fuel.monitoring.services.interfaces.GasStationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "01. Admin Controller", description = "API для администратора..")
@RequestMapping("/api/v1/admin")
@ApiResponses(@ApiResponse(responseCode = "200", useReturnTypeSchema = true))
public class AdminController {

    private final GasStationService gasStationService;

    @Operation(
            summary = "Добавление новой сети заправок",
            description = """
                    Добавление новой сети заправок в БД, если такой еще не существует.
                    """)
    @PostMapping("/gas_station")
    public ResponseEntity<DataResponse> addNewGasStation(@RequestBody AddGasStationRequest gasStationRequest) {
        gasStationService.addNewGasStation(gasStationRequest);
        return ResponseEntity.ok().body(new DataResponse(true));
    }


    @Operation(
            summary = "Добавление нового адреса в определённую сеть заправок",
            description = """
                    Добавление нового адреса в определённую сеть заправок.
                    """)
    @PostMapping("/gas_station_address")
    public ResponseEntity<DataResponse> addNewGasStationAddress(@RequestBody AddGasStationAddressRequest addGasStationAddressRequest) {
        gasStationService.addNewGasStationAddress(addGasStationAddressRequest);
        return ResponseEntity.ok().body(new DataResponse(true));
    }

    @Operation(
            summary = "Удаление сети заправок",
            description = """
                    Добавление новой сети заправок в БД, если такой еще не существует.
                    """)
    @DeleteMapping("/gas_station/{gasStationId}")
    public ResponseEntity<DataResponse> deleteGasStation(@PathVariable Long gasStationId) {
        gasStationService.deleteGasStationById(gasStationId);
        return ResponseEntity.ok().body(new DataResponse(true));
    }


    @Operation(
            summary = "Удаление адреса из определённой сети заправок",
            description = """
                    Удаление адреса из определённую сеть заправок.
                    """)
    @DeleteMapping("/gas_station_address/{gasStationAddressId}")
    public ResponseEntity<DataResponse> deleteGasStationAddress(@PathVariable Long gasStationAddressId) {
        gasStationService.deleteGasStationAddressById(gasStationAddressId);
        return ResponseEntity.ok().body(new DataResponse(true));
    }
}
