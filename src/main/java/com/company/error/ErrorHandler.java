package com.company.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Slf4j
@AllArgsConstructor
@ControllerAdvice
public class ErrorHandler {

  private static final String ERROR_MESSAGE = "An error has occurred: {}";

  private ObjectMapper jacksonObjectMapper;

  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(RuntimeException.class)
  public ApiError handleGenericError(RuntimeException ex) {
    log.error(ERROR_MESSAGE, ex.getMessage(), ex);
    return new ApiError(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(IllegalArgumentException.class)
  public ApiError handleIllegalArgumentError(IllegalArgumentException e) {
    log.error(ERROR_MESSAGE, e.getMessage(), e);
    return new ApiError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(BusinessException.class)
  public ApiError handleBusinessError(BusinessException e) {
    log.info(ERROR_MESSAGE, e.getMessage());
    return new ApiError(e.getMessage(), HttpStatus.CONFLICT);
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public ApiError handleNotFoundError(NotFoundException e) {
    log.debug("Entity not found: {}", e.getMessage());
    return new ApiError(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ApiError handleBadRequestError(MethodArgumentNotValidException ex) {
    Map<String, String> errors = ex.getBindingResult().getAllErrors().stream()
        .collect(toMap(err -> ((FieldError) err).getField(), ObjectError::getDefaultMessage));
    return new ApiError(toJson(errors), HttpStatus.BAD_REQUEST);
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
  public ApiError handleBadRequestError(HttpMediaTypeNotAcceptableException e) {
    log.info("Invalid MIME type: {}", e.getMessage());
    return new ApiError("Invalid MIME type", HttpStatus.BAD_REQUEST);
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = HttpMessageNotReadableException.class)
  protected ApiError handleBadRequestError(HttpMessageNotReadableException e) {
    return new ApiError("Malformed JSON request", HttpStatus.BAD_REQUEST);
  }


  @SneakyThrows
  private String toJson(Object value) {
    return jacksonObjectMapper.writeValueAsString(value);
  }
}
