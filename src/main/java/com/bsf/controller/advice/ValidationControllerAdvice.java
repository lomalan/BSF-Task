package com.bsf.controller.advice;

import com.bsf.model.response.RestError;
import java.time.LocalDateTime;
import javax.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ValidationControllerAdvice {


  @ExceptionHandler({ValidationException.class, MethodArgumentNotValidException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  RestError handleConstraintViolationException(Exception e) {
    return RestError.builder()
        .message(e.getMessage())
        .timeStamp(LocalDateTime.now())
        .build();
  }

}
