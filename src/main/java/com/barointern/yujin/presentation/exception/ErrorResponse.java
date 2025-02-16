package com.barointern.yujin.presentation.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

  private final int status;
  private final String message;
  private List<String> errors;

  public static ErrorResponse of(HttpStatus httpStatus, String message) {
    return new ErrorResponse(httpStatus.value(), message, null);
  }

}
