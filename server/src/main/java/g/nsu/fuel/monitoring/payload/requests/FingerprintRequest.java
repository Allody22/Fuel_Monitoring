package g.nsu.fuel.monitoring.payload.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FingerprintRequest {

    @Schema(description = "Фингерпринт пользователя.", example = "hello world i made it")
    @NotNull(message = "Пожалуйста, введите фингерпринт")
    @NotBlank(message = "Пожалуйста, введите фингерпринт")
    private String fingerprint;

}
