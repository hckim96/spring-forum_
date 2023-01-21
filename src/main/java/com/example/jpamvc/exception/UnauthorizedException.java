package com.example.jpamvc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// error.   ...-> find in MessageSource
@ResponseStatus(code = HttpStatus.UNAUTHORIZED , reason = "error.unauthorized")
public class UnauthorizedException extends RuntimeException{
}
