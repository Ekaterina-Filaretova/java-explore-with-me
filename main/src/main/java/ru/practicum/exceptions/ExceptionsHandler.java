package ru.practicum.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleObjectAlreadyExistException(final ObjectAlreadyExistException exception) {
        log.info("Возникло исключение: {}", exception.toString());
        return new ExceptionResponse(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleValidationException(final ValidationException exception) {
        log.info("Возникло исключение: {}", exception.toString());
        return new ExceptionResponse(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleObjectNotFoundException(final ObjectNotFoundException exception) {
        log.info("Возникло исключение: {}", exception.toString());
        return new ExceptionResponse(exception.getMessage());
    }
}
