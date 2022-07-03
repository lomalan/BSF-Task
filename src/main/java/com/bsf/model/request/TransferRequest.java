package com.bsf.model.request;

import java.math.BigInteger;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferRequest {
  @NotEmpty
  private String clientNumber;
  @NotEmpty
  private String accountNumberFrom;
  @NotEmpty
  private String accountNumberTo;
  @Min(1)
  private BigInteger amount;
}
