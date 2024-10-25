package g.nsu.fuel.monitoring.configuration.advices;

import g.nsu.fuel.monitoring.model.exception.NotInDataBaseException;
import g.nsu.fuel.monitoring.model.exception.UserAlreadyExistException;
import g.nsu.fuel.monitoring.payload.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleAllOtherExceptions(Exception exception) {
        return "Unexpected error occurred: " + exception.getMessage();
    }

    @ExceptionHandler(value = NotInDataBaseException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotInDataBaseException(NotInDataBaseException exception) {
        log.error("not in database exception {}", exception.getMessage());
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(value = UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleUserAlreadyExistException(UserAlreadyExistException exception) {
        log.error("user already exist exception {}", exception.getMessage());
        return new ErrorResponse(exception.getMessage());
    }
}
