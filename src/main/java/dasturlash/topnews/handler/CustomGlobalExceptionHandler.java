package dasturlash.topnews.handler;

import dasturlash.topnews.exceptions.AppBadException;
import dasturlash.topnews.exceptions.CheckVerificationCodeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        List<String> errors = new LinkedList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);
    }

        @ExceptionHandler(CheckVerificationCodeException.class)
        public ResponseEntity<Object> exceptionHandler(CheckVerificationCodeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }

        @ExceptionHandler(UsernameNotFoundException.class)
        public ResponseEntity<Object> exceptionHandler(UsernameNotFoundException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }

        @ExceptionHandler(AppBadException.class)
        public ResponseEntity<Object> exceptionHandler(AppBadException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
}
