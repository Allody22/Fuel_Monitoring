package g.nsu.fuel.monitoring.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    @Schema(name = "JWT токен для работы с профилем и запроса")
    private String access_token;

    @Schema(name = "Время жизни JWT")
    private Long expires_in;
}
