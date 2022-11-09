package ru.practicum.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleObjectAlreadyExistException(final ObjectAlreadyExistException exception) {
        log.info("Возникло исключение: {}", exception.toString());
        return new ExceptionResponse(exception, "Объект уже существует", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleValidationException(final ValidationException exception) {
        log.info("Возникло исключение: {}", exception.toString());
        return new ExceptionResponse(exception, "Ошибка запроса", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleObjectNotFoundException(final ObjectNotFoundException exception) {
        log.info("Возникло исключение: {}", exception.toString());
        return new ExceptionResponse(exception, "Объект не найден", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleDataIntegrityViolationException(final DataIntegrityViolationException exception) {
        log.info("Возникло исключение: {}", exception.toString());
        return new ExceptionResponse(exception, "Ошибка приложения", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleConstraintViolationException(final ConstraintViolationException exception) {
        log.info("Возникло исключение: {}", exception.toString());
        ExceptionResponse response = new ExceptionResponse(exception, "Ошибка запроса", HttpStatus.BAD_REQUEST);
        response.setMessage("Значение ".concat(exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList())
                .get(0)));
        return response;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        log.info("Возникло исключение: {}", exception.toString());
        ExceptionResponse response = new ExceptionResponse(exception, "Ошибка запроса", HttpStatus.BAD_REQUEST);
        response.setMessage(Objects.requireNonNull(exception.getBindingResult()
                        .getFieldError())
                .getDefaultMessage());
        return response;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleConversionFailedException(final ConversionFailedException exception) {
        log.info("Возникло исключение: {}", exception.toString());
        ExceptionResponse response = new ExceptionResponse(exception, "Ошибка запроса", HttpStatus.BAD_REQUEST);
        response.setMessage("Передан неподдерживаемый формат сортировки");
        return response;
    }
}
