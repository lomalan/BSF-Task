package com.bsf.model.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestError {
  private String message;
  private LocalDateTime timeStamp;
}
