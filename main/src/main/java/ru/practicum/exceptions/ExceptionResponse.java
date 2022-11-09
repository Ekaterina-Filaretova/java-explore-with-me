package ru.practicum.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse {

    private final List<String> errors;
    private String message;
    private final String reason;
    private final String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp;

    public ExceptionResponse(Exception ex, String reason, HttpStatus status) {
        this.errors = List.of(ex.getClass().getName());
        this.message = ex.getMessage();
        this.reason = reason;
        this.status = status.toString();
        this.timestamp = LocalDateTime.now();
    }
}