package com.bsf.service;

import com.bsf.model.entity.Account;
import com.bsf.model.entity.Client;
import com.bsf.model.request.RegisterRequest;
import com.bsf.model.response.RegisterResponse;
import com.bsf.repository.ClientRepository;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

  private final ClientRepository clientRepository;

  public ClientService(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  public RegisterResponse registerClient(RegisterRequest request) {
    checkIfEmailExists(request);
    Client client = createClient(request, createAccount(request));
    Client savedClient = clientRepository.save(client);
    Account savedAccount = savedClient.getAccounts().iterator().next();
    return RegisterResponse.builder()
        .balance(savedAccount.getBalance())
        .accountNumber(savedAccount.getNumber())
        .accountExpirationDate(savedAccount.getExpirationDate())
        .clientNumber(savedClient.getNumber())
        .build();
  }

  private void checkIfEmailExists(RegisterRequest request) {
    clientRepository.findClientByEmail(request.getEmail())
        .ifPresent(client -> {
          throw new IllegalStateException("Client with this email already exists");
        });
  }

  private Account createAccount(RegisterRequest request) {
    Account account = new Account();
    account.setNumber(UUID.randomUUID().toString());
    account.setBalance(request.getDepositAmount());
    account.setExpirationDate(LocalDate.now().plusYears(5));
    return account;
  }

  private Client createClient(RegisterRequest request, Account account) {
    Client client = new Client();
    client.setFirstName(request.getFirstName());
    client.setLastName(request.getLastName());
    client.setNumber(UUID.randomUUID().toString());
    client.setEmail(request.getEmail());
    Set<Account> accounts = new HashSet<>();
    accounts.add(account);
    client.setAccounts(accounts);
    return client;
  }
}
