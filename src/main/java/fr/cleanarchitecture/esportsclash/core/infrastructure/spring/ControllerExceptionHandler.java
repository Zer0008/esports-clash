package fr.cleanarchitecture.esportsclash.core.infrastructure.spring;

import fr.cleanarchitecture.esportsclash.core.domain.exceptions.BadRequestException;
import fr.cleanarchitecture.esportsclash.core.domain.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleIBadRequestException(BadRequestException e) {
        return ResponseEntity.badRequest().build();
    }
}
