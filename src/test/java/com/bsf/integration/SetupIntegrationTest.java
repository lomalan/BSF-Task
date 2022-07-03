package com.bsf.integration;

import com.bsf.model.entity.Account;
import com.bsf.model.entity.Client;
import com.bsf.model.entity.Transaction;
import com.bsf.repository.ClientRepository;
import com.bsf.repository.TransactionRepository;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import javax.annotation.Resource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class SetupIntegrationTest {

  public static final String FIRST_CLIENT_NUMBER = "1111";

  public static final String SECOND_CLIENT_NUMBER = "2222";
  public static final String FIRST_ACCOUNT_NUMBER = "12345";
  public static final String SECOND_ACCOUNT_NUMBER = "54321";

  @Resource
  private TransactionRepository transactionRepository;

  @Resource
  private ClientRepository clientRepository;

  @BeforeEach
  public void setup() {

    Account firstAccount = createAccount(FIRST_ACCOUNT_NUMBER, new BigInteger("3000"));
    Account secondAccount = createAccount(SECOND_ACCOUNT_NUMBER, new BigInteger("5000"));
    List<Client> clients = Arrays.asList(createClient(FIRST_CLIENT_NUMBER, "aa@bb.com", firstAccount),
        createClient(SECOND_CLIENT_NUMBER, "bb@aa.com", secondAccount));

    Iterable<Client> savedAccounts = clientRepository.saveAll(clients);
    Iterator<Client> iterator = savedAccounts.iterator();
    Account firstAcc = iterator.next().getAccounts().stream().findFirst().orElse(null);
    Account secondAcc = iterator.next().getAccounts().stream().findFirst().orElse(null);

    IntStream.range(1, 6)
        .forEach(index -> saveTransaction(firstAcc, secondAcc, String.valueOf(index)));
    IntStream.range(6, 11)
        .forEach(index -> saveTransaction(secondAcc, firstAcc, String.valueOf(index)));
  }

  @AfterEach
  public void cleanup() {
    transactionRepository.deleteAll();
    clientRepository.deleteAll();
  }

  private void saveTransaction(Account from, Account to, String index) {
    transactionRepository
        .save(createTransaction(from, to, BigInteger.TEN.multiply(new BigInteger(index))));
  }

  private Transaction createTransaction(Account from, Account to, BigInteger amount) {
    Transaction transaction = new Transaction();
    transaction.setAmount(amount);
    transaction.setNumber(UUID.randomUUID().toString());
    transaction.setFrom(from);
    transaction.setTo(to);
    transaction.setTimeStamp(LocalDateTime.now());
    return transaction;
  }

  private Account createAccount(String accountNumber, BigInteger balance) {
    Account account = new Account();
    account.setNumber(accountNumber);
    account.setBalance(balance);
    account.setExpirationDate(LocalDate.now().plusYears(5));
    return account;
  }

  private Client createClient(String clientNumber, String email, Account account) {
    Client client = new Client();
    client.setNumber(clientNumber);
    client.setFirstName("First");
    client.setLastName("Last");
    client.setEmail(email);
    client.setAccounts(Collections.singleton(account));
    return client;
  }
}
