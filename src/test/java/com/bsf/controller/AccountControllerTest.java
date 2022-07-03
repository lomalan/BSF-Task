package com.bsf.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bsf.service.AccountDetailsService;
import com.bsf.service.ClientService;
import java.math.BigInteger;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest {

  private static final String ACC_DETAILS_ENDPOINT = "/account/details";
  @Autowired
  private WebApplicationContext context;

  @MockBean
  private AccountDetailsService accountDetailsService;

  protected MockMvc mvc;

  @BeforeEach
  public void setup() {
    this.mvc = MockMvcBuilders
        .webAppContextSetup(this.context)
        .build();

  }

  @Test
  void accDetailsOfEmptyAccNumber() throws Exception {
    mvc.perform(get(ACC_DETAILS_ENDPOINT)
            .param("accountNumber", StringUtils.EMPTY))
        .andExpect(status().isBadRequest());
  }

  @Test
  void provideAccDetails() throws Exception {
    mvc.perform(get(ACC_DETAILS_ENDPOINT)
            .param("accountNumber", "12345")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
}
