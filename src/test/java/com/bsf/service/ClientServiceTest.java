package com.bsf.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.bsf.integration.SetupIntegrationTest;
import com.bsf.model.entity.Client;
import com.bsf.model.request.RegisterRequest;
import com.bsf.model.response.RegisterResponse;
import com.bsf.repository.ClientRepository;
import java.math.BigInteger;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;

class ClientServiceTest extends SetupIntegrationTest {

  @Resource
  private ClientService clientService;
  @Resource
  private ClientRepository clientRepository;

  @Test
  void successfulRegistration() {
    RegisterRequest registerRequest = createClientForRegistration();

    RegisterResponse registerResponse = clientService.registerClient(registerRequest);
    Client client = clientRepository.findClientByNumber(registerResponse.getClientNumber()).orElse(null);
    assertNotNull(client);
    assertEquals(registerRequest.getEmail(), client.getEmail());
    assertEquals(registerRequest.getFirstName(), client.getFirstName());
    assertFalse(client.getAccounts().isEmpty());
  }

  @Test
  void registrationWithExistingEmail() {
    RegisterRequest registerRequest = createClientForRegistration();
    clientService.registerClient(registerRequest);

    IllegalStateException exception = assertThrows(IllegalStateException.class,
        () -> clientService.registerClient(createClientForRegistration()));
    assertEquals("Client with this email already exists", exception.getMessage());
  }

  private RegisterRequest createClientForRegistration() {
    return RegisterRequest.builder()
        .depositAmount(new BigInteger("1000"))
        .email("some@mail.com")
        .firstName("Alex")
        .lastName("Jones")
        .build();
  }
}
