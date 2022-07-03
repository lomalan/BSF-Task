package com.bsf.validator.request;

import static com.bsf.validation.request.TransferRequestValidator.SAME_ACCOUNT_MSG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.bsf.model.request.TransferRequest;
import com.bsf.validation.request.TransferRequestValidator;
import java.math.BigInteger;
import javax.validation.ValidationException;
import org.junit.jupiter.api.Test;


class TransferRequestValidatorTest {

  private final TransferRequestValidator transferRequestValidator = new TransferRequestValidator();

  @Test
  void sameAccountValidation() {
    ValidationException validationException = assertThrows(ValidationException.class,
        () -> transferRequestValidator.validateTransferRequest(createRequest("1", "1")));

    assertEquals(SAME_ACCOUNT_MSG, validationException.getMessage());
  }

  private TransferRequest createRequest(String numberFrom, String numberTo) {
    return TransferRequest.builder()
        .accountNumberFrom(numberFrom)
        .accountNumberTo(numberTo)
        .amount(BigInteger.TEN)
        .build();
  }
}
