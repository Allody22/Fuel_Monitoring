package g.nsu.fuel.monitoring.controller;

import g.nsu.fuel.monitoring.payload.response.GasStationResponse;
import g.nsu.fuel.monitoring.payload.response.GasStationSummary;
import g.nsu.fuel.monitoring.services.interfaces.GasStationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "03. Gas Station Controller", description = "API для получения АЗС, получение сводки по ним, их адресов..")
@ApiResponses(@ApiResponse(responseCode = "200", useReturnTypeSchema = true))
@RequestMapping("/api/v1/stations")
public class GasStationsController {

    private final GasStationService gasStationService;

    @Operation(
            summary = "Получение информации о сетях заправок в нашей БД",
            description = """
                    Получение всех компаний АЗС.
                    """)
    @GetMapping("/all")
    public ResponseEntity<List<GasStationResponse>> getAllGasStation() {
        return ResponseEntity.ok().body(gasStationService.getAllGasStations());
    }

    @Operation(
            summary = "Получение сводке обо всей заправке",
            description = """
                    Передаётся айди определённого заправки и количество дней.
                    Возвращается информации об изменении цен на это филиале АЗС для существующих типов топлива за запрашиваемый интервальный день.
                    Учитывается каждый интервал этой заправки и передаётся средняя цена за день на этих интервалах.
                    """)
    @GetMapping("/summary/{id}")
    public ResponseEntity<GasStationSummary> getGasStationSummary(@PathVariable Long id, @RequestParam(defaultValue = "30") int interval) {
        return ResponseEntity.ok().body(gasStationService.getGasStationSummary(id, interval));
    }
}
