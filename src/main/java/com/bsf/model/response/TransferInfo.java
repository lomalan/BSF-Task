package com.bsf.model.response;

import java.math.BigInteger;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferInfo {
  private String transactionNumber;
  private String accountNumberFrom;
  private String accountNumberTo;
  private LocalDateTime transactionDate;
  private BigInteger amount;
}
