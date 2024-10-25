package g.nsu.fuel.monitoring.model.exception.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FingerPrintException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public FingerPrintException() {
        super("Error with sessions. Try to login again.");
    }

    public FingerPrintException(String message) {
        super(message);
    }
}
