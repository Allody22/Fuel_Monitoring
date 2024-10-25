package g.nsu.fuel.monitoring.payload.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RefreshFingerprintRequest {

    @Schema(description = "Рефреш токен пользователя.", example = "1234-1234-1234-1234")
    private String refreshToken;

    @Schema(description = "Фингерпринт пользователя.", example = "hello world i made it")
    private String fingerprint;

}
