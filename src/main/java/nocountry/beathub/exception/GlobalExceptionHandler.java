package nocountry.beathub.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LicenciaDuplicadaException.class)
    public ResponseEntity<String> handleLicenciaDuplicadaException(LicenciaDuplicadaException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(LicenciaNoExisteException.class)
    public ResponseEntity<String> handleLicenciaNoExisteException(LicenciaNoExisteException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

