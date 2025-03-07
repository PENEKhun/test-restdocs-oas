package org.penekhun.restdocs.global.exception;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

  private final ExceptionCode exceptionCode;

  public CustomException(@NotNull final ExceptionCode exceptionCode) {
    this.exceptionCode = exceptionCode;
  }

}
