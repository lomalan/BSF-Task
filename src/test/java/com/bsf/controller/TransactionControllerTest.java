package com.bsf.controller;

import static com.bsf.controller.ControllerHelper.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bsf.model.request.TransferRequest;
import com.bsf.service.TransferService;
import com.bsf.validation.request.TransferRequestValidator;
import java.math.BigInteger;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = TransactionController.class)
class TransactionControllerTest {

  private static final String TRANSFER_ENDPOINT = "/transaction/transfer";

  @Autowired
  private WebApplicationContext context;

  @MockBean
  private TransferService transferService;
  @MockBean
  private TransferRequestValidator transferRequestValidator;

  protected MockMvc mvc;

  @BeforeEach
  public void setup() {
    this.mvc = MockMvcBuilders
        .webAppContextSetup(this.context)
        .build();

  }

  @Test
  void transferComplete() throws Exception {
    mvc.perform(post(TRANSFER_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(buildTransferJson("654", new BigInteger("100"))))
        .andExpect(status().isOk());
  }

  @Test
  void transferWithInvalidAmount() throws Exception {
    mvc.perform(post(TRANSFER_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(buildTransferJson("777", BigInteger.ZERO)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void transferWithEmptyAccountNumber() throws Exception {
    mvc.perform(post(TRANSFER_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(buildTransferJson(StringUtils.EMPTY, BigInteger.TEN)))
        .andExpect(status().isBadRequest());
  }

  public String buildTransferJson(String accountFrom, BigInteger amount) {
    return asJsonString(TransferRequest.builder()
        .clientNumber("12345")
        .accountNumberFrom(accountFrom)
        .accountNumberTo("555")
        .amount(amount)
        .build());
  }
}
