package com.bsf.service;

import com.bsf.model.entity.Account;
import com.bsf.model.entity.Transaction;
import com.bsf.model.request.TransferRequest;
import com.bsf.model.response.TransferInfo;
import com.bsf.repository.AccountRepository;
import com.bsf.repository.ClientRepository;
import com.bsf.repository.TransactionRepository;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TransferService {

  private final TransactionRepository transactionRepository;
  private final AccountRepository accountRepository;
  private final ClientRepository clientRepository;

  public TransferService(TransactionRepository transactionRepository, AccountRepository accountRepository,
      ClientRepository clientRepository) {
    this.transactionRepository = transactionRepository;
    this.accountRepository = accountRepository;
    this.clientRepository = clientRepository;
  }

  public TransferInfo transfer(TransferRequest request) {
    Transaction transaction = createTransaction(request);
    saveToDatabase(transaction);
    return TransferInfo.builder()
        .transactionNumber(transaction.getNumber())
        .accountNumberFrom(transaction.getFrom().getNumber())
        .accountNumberTo(transaction.getTo().getNumber())
        .transactionDate(transaction.getTimeStamp())
        .amount(transaction.getAmount())
        .build();
  }

  private void saveToDatabase(Transaction transaction) {
    transactionRepository.save(transaction);
    accountRepository.saveAll(Set.of(transaction.getFrom(), transaction.getTo()));
  }

  private void calculateBalances(TransferRequest request, Account from, Account to) {
    from.setBalance(from.getBalance().subtract(request.getAmount()));
    to.setBalance(to.getBalance().add(request.getAmount()));
  }

  private Transaction createTransaction(TransferRequest request) {
    Account from = findClientAccount(request);
    checkSenderBalance(from, request.getAmount());
    Account to = getAccountByNumber(request);
    calculateBalances(request, from, to);
    Transaction transaction = new Transaction();
    transaction.setFrom(from);
    transaction.setTo(to);
    transaction.setAmount(request.getAmount());
    transaction.setNumber(UUID.randomUUID().toString());
    transaction.setTimeStamp(LocalDateTime.now());
    return transaction;
  }

  private Account findClientAccount(TransferRequest request) {
    return clientRepository.findClientByNumber(request.getClientNumber())
        .orElseThrow(() -> new IllegalStateException("Client is not found"))
        .getAccounts().stream()
        .filter(account -> account.getNumber().equals(request.getAccountNumberFrom()))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Sender account is not found"));
  }

  private Account getAccountByNumber(TransferRequest request) {
    return accountRepository.findAccountByNumber(request.getAccountNumberTo())
        .orElseThrow(() -> new IllegalStateException("Receiver account is not found"));
  }

  private void checkSenderBalance(Account from, BigInteger amount) {
    if (from.getBalance().compareTo(amount) <= 0) {
      throw new IllegalStateException("Sender account haven't got sufficient funds on your account for transfer");
    }
  }
}
