package g.nsu.fuel.monitoring.model.exception.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenExpiredException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public TokenExpiredException() {
        super("You session has expired. Please login again.");
    }
}
