package br.com.baseproject.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AccessDeniedRequestException extends AccessDeniedException {
    public AccessDeniedRequestException(String msg) {
        super(msg);
    }
}
