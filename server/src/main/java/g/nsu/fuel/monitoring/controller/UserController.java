package g.nsu.fuel.monitoring.controller;

import g.nsu.fuel.monitoring.payload.requests.FavoritesRequest;
import g.nsu.fuel.monitoring.payload.response.DataResponse;
import g.nsu.fuel.monitoring.services.interfaces.GasStationService;
import g.nsu.fuel.monitoring.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialException;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "04. User Controller", description = "API для получения работы с профилем юзера.")
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Добавление нового адреса АЗС определённой сети в избранное.",
            description = """
                    Получается айди АЗС для определённого адреса и оно добавляется в набор избранного.
                    """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Все типы топлива успешно получены.", content = @Content(schema = @Schema(implementation = DataResponse.class))),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @PostMapping("/favorites/{stationId}")
    public ResponseEntity<DataResponse> addFavorites(@AuthenticationPrincipal UserDetails user, @PathVariable Long stationId) throws CredentialException {
        userService.addFavorite(user,stationId);
        return ResponseEntity.ok().body(new DataResponse(true));
    }

    @Operation(
            summary = "Удаление адреса АЗС определённой сети из избранного.",
            description = """
                    Получается айди АЗС для определённого адреса и оно добавляется .
                    """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Все типы топлива успешно получены.", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @DeleteMapping("/favorites/{stationId}")
    public ResponseEntity<DataResponse> deleteFavorites(@AuthenticationPrincipal UserDetails user, @PathVariable Long stationId) throws CredentialException {
        userService.deleteFavorite(user,stationId);
        return ResponseEntity.ok().body(new DataResponse(true));
    }
}
