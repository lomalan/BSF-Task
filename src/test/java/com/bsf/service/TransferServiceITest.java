package com.bsf.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.bsf.integration.SetupIntegrationTest;
import com.bsf.model.request.TransferRequest;
import com.bsf.model.response.TransferInfo;
import java.math.BigInteger;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;

class TransferServiceITest extends SetupIntegrationTest {

  @Resource
  private TransferService transferService;


  @Test
  void transferFromNonExistentClient() {
    TransferRequest request = getRequest("123467", FIRST_ACCOUNT_NUMBER,
        "12", new BigInteger("5"));
    IllegalStateException exception = assertThrows(IllegalStateException.class,
        () -> transferService.transfer(request));
    assertEquals("Client is not found", exception.getMessage());
  }

  @Test
  void transferToNonExistentAccount() {
    TransferRequest request = getRequest(FIRST_CLIENT_NUMBER, FIRST_ACCOUNT_NUMBER,
        "12", new BigInteger("5"));
    IllegalStateException exception = assertThrows(IllegalStateException.class,
        () -> transferService.transfer(request));
    assertEquals("Receiver account is not found", exception.getMessage());
  }

  @Test
  void transferWithNonExistentAccountForClient() {
    TransferRequest request = getRequest(FIRST_CLIENT_NUMBER, SECOND_ACCOUNT_NUMBER,
        FIRST_ACCOUNT_NUMBER, new BigInteger("10000"));
    IllegalStateException exception = assertThrows(IllegalStateException.class,
        () -> transferService.transfer(request));
    assertEquals("Sender account is not found", exception.getMessage());
  }

  @Test
  void transferBetweenTwoAccountsNotSufficientFunds() {
    TransferRequest request = getRequest(FIRST_CLIENT_NUMBER, FIRST_ACCOUNT_NUMBER,
        SECOND_ACCOUNT_NUMBER, new BigInteger("10000"));
    IllegalStateException exception = assertThrows(IllegalStateException.class,
        () -> transferService.transfer(request));
    assertEquals("Sender account haven't got sufficient funds on your account for transfer",
        exception.getMessage());
  }

  @Test
  void transferBetweenTwoAccounts() {
    TransferRequest request = getRequest(FIRST_CLIENT_NUMBER, FIRST_ACCOUNT_NUMBER,
        SECOND_ACCOUNT_NUMBER, new BigInteger("50"));
    TransferInfo transfer = transferService.transfer(request);
    assertNotNull(transfer);
    assertEquals(request.getAccountNumberFrom(), transfer.getAccountNumberFrom());
  }

  private TransferRequest getRequest(String clientFrom, String accountFrom, String accountTo, BigInteger amount) {
    return TransferRequest.builder()
        .clientNumber(clientFrom)
        .accountNumberFrom(accountFrom)
        .accountNumberTo(accountTo)
        .amount(amount)
        .build();
  }
}
