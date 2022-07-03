package com.bsf.model.response;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDetails {
  private String accountNumber;
  private LocalDate accountExpirationDate;
  private List<TransferInfo> transferHistory;
  private BigInteger balance;
}
