package com.bsf.controller;

import static com.bsf.controller.ControllerHelper.asJsonString;
import static java.nio.file.Paths.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bsf.model.request.RegisterRequest;
import com.bsf.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@WebMvcTest(controllers = ClientController.class)
class ClientControllerTest {

  @Autowired
  private WebApplicationContext context;

  @MockBean
  private ClientService clientService;

  protected MockMvc mvc;

  @BeforeEach
  public void setup() {
    this.mvc = MockMvcBuilders
        .webAppContextSetup(this.context)
        .build();

  }

  @Test
  void registrationComplete() throws Exception {
    mvc.perform(post("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(buildRegisterJson("aa@bb.com", new BigInteger("100"))))
        .andExpect(status().isOk());
  }

  @Test
  void tryToRegisterWithInvalidAmount() throws Exception {
    mvc.perform(post("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(buildRegisterJson("aa@bb.com", new BigInteger("-10"))))
        .andExpect(status().isBadRequest());
  }

  @Test
  void tryToRegisterWithInvalidEmail() throws Exception {
    mvc.perform(post("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(buildRegisterJson("aa", new BigInteger("10"))))
        .andExpect(status().isBadRequest());
  }

  public String buildRegisterJson(String email, BigInteger depositAmount) {
    return asJsonString(RegisterRequest.builder()
        .firstName("First")
        .lastName("Last")
        .email(email)
        .depositAmount(depositAmount)
        .build());
  }
}
