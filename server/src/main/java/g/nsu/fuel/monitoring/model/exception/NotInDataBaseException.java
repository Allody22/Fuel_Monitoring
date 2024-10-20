package g.nsu.fuel.monitoring.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotInDataBaseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public NotInDataBaseException(String message) {
        super(message + " не найдена в базе данных.");
    }
}
