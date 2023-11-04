package com.uob.mathpuzzle.exception;

import com.uob.mathpuzzle.dto.ErrorMessageResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.uob.mathpuzzle.constant.ApplicationConstant.APPLICATION_ERROR_OCCURRED_MESSAGE;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    /*@ExceptionHandler(value = {Exception.class})
    ResponseEntity<ErrorMessageResponseDTO> handleAnyException(Exception ex, WebRequest webRequest) {
        return new ResponseEntity<>(
                new ErrorMessageResponseDTO(false, 100, APPLICATION_ERROR_OCCURRED_MESSAGE), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {GameException.class})
    ResponseEntity<ErrorMessageResponseDTO> handleGameException(GameException ex, WebRequest webRequest) {
        return new ResponseEntity<>(
                new ErrorMessageResponseDTO(false, ex.getStatus(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
*/

}
