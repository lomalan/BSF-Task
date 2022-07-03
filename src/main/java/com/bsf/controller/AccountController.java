package com.bsf.controller;

import com.bsf.model.response.AccountDetails;
import com.bsf.service.AccountDetailsService;
import javax.validation.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@Validated
public class AccountController {

  private final AccountDetailsService accountDetailsService;

  public AccountController(AccountDetailsService accountDetailsService) {
    this.accountDetailsService = accountDetailsService;
  }

  @GetMapping("/details")
  ResponseEntity<AccountDetails> getAccountDetails(@RequestParam @NotEmpty String accountNumber) {
    return ResponseEntity.ok()
        .body(accountDetailsService.getAccountDetails(accountNumber));
  }
}
