package com.bsf.model.request;

import java.math.BigInteger;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
  @NotEmpty
  private String firstName;
  @NotEmpty
  private String lastName;
  @NotEmpty
  @Email
  private String email;
  @Min(1)
  private BigInteger depositAmount;
}
