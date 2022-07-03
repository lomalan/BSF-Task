package com.bsf.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.bsf.integration.SetupIntegrationTest;
import com.bsf.model.response.AccountDetails;
import com.bsf.model.response.TransferInfo;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;

class AccountDetailsServiceITest extends SetupIntegrationTest {

  @Resource
  private AccountDetailsService accountDetailsService;

  @Test
  void getFirstAccountInfo() {
    AccountDetails accountDetails = accountDetailsService.getAccountDetails(FIRST_ACCOUNT_NUMBER);
    assertNotNull(accountDetails);
    assertEquals(FIRST_ACCOUNT_NUMBER, accountDetails.getAccountNumber());
    assertEquals(10, accountDetails.getTransferHistory().size());
    List<TransferInfo> incomingTransactions = accountDetails.getTransferHistory().stream()
        .filter(history -> history.getAmount().compareTo(BigInteger.ZERO) < 0)
        .collect(Collectors.toList());
    incomingTransactions.forEach(incomingTransfers -> {
      assertNull(incomingTransfers.getAccountNumberFrom());
    });
  }

}
