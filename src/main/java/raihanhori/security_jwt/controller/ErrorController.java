package raihanhori.security_jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import raihanhori.security_jwt.helper.ErrorApiResponseHelper;

@ControllerAdvice
public class ErrorController {

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ErrorApiResponseHelper> responseStatusException(ResponseStatusException exception) {
		int statusCode = exception.getStatusCode().value();
		return ResponseEntity.status(exception.getStatusCode()).body(ErrorApiResponseHelper.builder()
				.statusCode(statusCode)
				.message(statusCode != 401 ? exception.getReason() : "unauthorized")
				.build());
	}

}
