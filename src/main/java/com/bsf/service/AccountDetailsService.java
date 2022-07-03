package com.bsf.service;

import com.bsf.model.entity.Account;
import com.bsf.model.entity.Transaction;
import com.bsf.model.response.AccountDetails;
import com.bsf.model.response.TransferInfo;
import com.bsf.repository.AccountRepository;
import com.bsf.repository.TransactionRepository;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsService {

  private final AccountRepository accountRepository;
  private final TransactionRepository transactionRepository;

  public AccountDetailsService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;
  }

  public AccountDetails getAccountDetails(String accountNumber) {
    Account account = getAccount(accountNumber);
    List<TransferInfo> incomingTransactions = createTransferHistory(account, true);
    List<TransferInfo> outgoingTransactions = createTransferHistory(account, false);
    return AccountDetails.builder()
        .balance(account.getBalance())
        .accountNumber(accountNumber)
        .accountExpirationDate(account.getExpirationDate())
        .transferHistory(constructTransferHistory(incomingTransactions, outgoingTransactions))
        .build();
  }

  private Account getAccount(String accountNumber) {
    return accountRepository.findAccountByNumber(accountNumber)
        .orElseThrow(() -> new IllegalStateException("Account with this number not found"));
  }

  private List<TransferInfo> createTransferHistory(Account account, boolean isIncoming) {
    return transactionRepository.findTransactionByTo(account).stream()
        .map(transaction -> createTransferInfo(transaction, isIncoming))
        .collect(Collectors.toList());
  }

  private TransferInfo createTransferInfo(Transaction transaction, boolean isIncoming) {
    TransferInfo.TransferInfoBuilder transferInfoBuilder = TransferInfo.builder()
        .transactionDate(transaction.getTimeStamp())
        .transactionNumber(transaction.getNumber());
    if (isIncoming) {
      transferInfoBuilder
          .accountNumberFrom(transaction.getFrom().getNumber())
          .amount(transaction.getAmount());
    } else {
      transferInfoBuilder
          .accountNumberTo(transaction.getTo().getNumber())
          .amount(transaction.getAmount().negate());
    }
    return transferInfoBuilder.build();
  }

  private List<TransferInfo> constructTransferHistory(List<TransferInfo> incomingTransactions,
      List<TransferInfo> outgoingTransactions) {
    return Stream.of(incomingTransactions, outgoingTransactions)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }
}
