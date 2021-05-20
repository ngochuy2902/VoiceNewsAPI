package com.news.voicenews.error.handler;

import com.news.voicenews.dto.res.ErrorMessageRes;
import com.news.voicenews.error.exception.ValidatorException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidatorException.class)
    public ResponseEntity<ErrorMessageRes> handleRuntimeException(ValidatorException ex) {
        ErrorMessageRes errorMessageRes = ErrorMessageRes.builder()
                                                         .message(ex.getMessage())
                                                         .fieldName(ex.getFieldName())
                                                         .build();

        return ResponseEntity.badRequest().body(errorMessageRes);
    }
}
