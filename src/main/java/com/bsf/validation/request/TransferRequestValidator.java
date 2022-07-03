package com.bsf.validation.request;

import com.bsf.model.request.TransferRequest;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class TransferRequestValidator {

  public static final String SAME_ACCOUNT_MSG = "You can't transfer money to the same account";

  public void validateTransferRequest(TransferRequest request) {
    List<String> messages = new ArrayList<>();
    validateAccountNumbers(request, messages);
    if (!messages.isEmpty()) {
      throw new ValidationException(String.join(", ", messages));
    }
  }


  private void validateAccountNumbers(TransferRequest request, List<String> messages) {
    if (request.getAccountNumberFrom().equals(request.getAccountNumberTo())) {
      messages.add(SAME_ACCOUNT_MSG);
    }
  }
}
