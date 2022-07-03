package com.bsf.controller;

import com.bsf.model.request.RegisterRequest;
import com.bsf.model.response.RegisterResponse;
import com.bsf.service.ClientService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

  private final ClientService clientService;

  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @PostMapping("/register")
  ResponseEntity<RegisterResponse> registerClient(@RequestBody @Valid RegisterRequest request) {
    return ResponseEntity.ok()
        .body(clientService.registerClient(request));
  }
}
