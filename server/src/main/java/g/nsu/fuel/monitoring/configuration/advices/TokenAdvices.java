package g.nsu.fuel.monitoring.configuration.advices;

import g.nsu.fuel.monitoring.model.exception.security.FingerPrintException;
import g.nsu.fuel.monitoring.model.exception.security.InvalidTokenException;
import g.nsu.fuel.monitoring.model.exception.security.TokenExpiredException;
import g.nsu.fuel.monitoring.payload.response.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class TokenAdvices {

    @ExceptionHandler(value = FingerPrintException.class)
    public ResponseEntity<ErrorResponse> handleFingerPrintException(FingerPrintException exception, WebRequest request) {
        log.error("Fingerprint exception {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Error with sessions. Try to login again"));
    }


    @ExceptionHandler(value = InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException exception, WebRequest request) {
        log.error("Invalid token exception {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Error with sessions. Try to login again."));
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleTokenExpiredException(TokenExpiredException exception, WebRequest request) {
        log.error("Token expired exception {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Session is expired. Please login again."));
    }
}
