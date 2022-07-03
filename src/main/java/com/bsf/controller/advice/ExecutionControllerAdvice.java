package com.bsf.controller.advice;

import com.bsf.model.response.RestError;
import java.time.LocalDateTime;
import org.hibernate.JDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ExecutionControllerAdvice {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionControllerAdvice.class);

  @ExceptionHandler({IllegalStateException.class, JDBCException.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public RestError restExceptions(Exception e) {
    LOGGER.error("Request failed with the exception: {}", e.getMessage(), e);
    return RestError.builder()
        .message(e.getMessage())
        .timeStamp(LocalDateTime.now())
        .build();
  }
}
