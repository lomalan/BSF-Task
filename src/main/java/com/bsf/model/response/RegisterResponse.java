package com.bsf.model.response;

import java.math.BigInteger;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
  private String clientNumber;
  private String accountNumber;
  private LocalDate accountExpirationDate;
  private BigInteger balance;
}
