package com.company.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class ApiError {

  private String message;
  private int status;

  @Builder.Default
  private LocalDateTime time = LocalDateTime.now();

  public ApiError(String message, HttpStatus status) {
    this.message = message;
    this.status = status.value();
    this.time = LocalDateTime.now();
  }

}
