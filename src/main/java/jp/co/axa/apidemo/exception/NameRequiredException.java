package jp.co.axa.apidemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Name is required")
public class NameRequiredException extends RuntimeException {
}
