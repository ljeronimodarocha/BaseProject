package br.com.baseproject.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserNotFoundException extends UsernameNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
