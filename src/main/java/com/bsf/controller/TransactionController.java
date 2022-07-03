package com.bsf.controller;

import com.bsf.model.request.TransferRequest;
import com.bsf.model.response.TransferInfo;
import com.bsf.service.TransferService;
import com.bsf.validation.request.TransferRequestValidator;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/transaction")
public class TransactionController {

  private final TransferService transferService;
  private final TransferRequestValidator transferRequestValidator;


  public TransactionController(TransferService transferService, TransferRequestValidator transferRequestValidator) {
    this.transferService = transferService;
    this.transferRequestValidator = transferRequestValidator;
  }

  @PostMapping("/transfer")
  ResponseEntity<TransferInfo> transferMoney(@RequestBody @Valid TransferRequest request) {
    transferRequestValidator.validateTransferRequest(request);
    return ResponseEntity.ok()
        .body(transferService.transfer(request));
  }
}
