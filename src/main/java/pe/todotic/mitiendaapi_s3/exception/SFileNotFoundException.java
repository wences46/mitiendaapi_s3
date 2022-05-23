package pe.todotic.mitiendaapi_s3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SFileNotFoundException extends RuntimeException {
    public SFileNotFoundException(String message) {
        super(message);
    }
    public SFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
