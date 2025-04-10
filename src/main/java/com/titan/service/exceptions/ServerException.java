package com.titan.service.exceptions;

public class ServerException extends RuntimeException {
  public ServerException(Exception e) {
    super(e);
  }

  public ServerException(String message) {
    super(message);
  }
}
