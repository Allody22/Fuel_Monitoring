package g.nsu.fuel.monitoring.controller;

import g.nsu.fuel.monitoring.services.AuthService;
import g.nsu.fuel.monitoring.services.TokensService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "00. Auth Controller", description = "API для работы с авторизацией, регистрацией и токенами.")
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final TokensService tokensService;

    private final AuthService authService;


}
