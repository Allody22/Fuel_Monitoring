package g.nsu.fuel.monitoring.controller;

import g.nsu.fuel.monitoring.services.interfaces.GasStationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "03. Gas Station Controller", description = "API для получения АЗС, получение сводки по ним, их адресов..")
@RequestMapping("/api/v1/oil")
public class GasStationsController {

    private final GasStationService gasStationService;

    @Operation(
            summary = "Получение информации о сетях заправок в нашей БД",
            description = """
                    Получение всех компаний АЗС.
                    """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Все типы топлива успешно получены.", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @PostMapping("/stations")
    public ResponseEntity<?> getAllGasStation() {
        return ResponseEntity.ok().body(gasStationService.getAllGasStations());
    }
}
