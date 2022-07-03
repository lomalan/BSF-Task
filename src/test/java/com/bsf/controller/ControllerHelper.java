package com.bsf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class ControllerHelper {

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
