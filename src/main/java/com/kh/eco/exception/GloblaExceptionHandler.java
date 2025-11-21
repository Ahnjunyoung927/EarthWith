package com.kh.eco.exception;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GloblaExceptionHandler {

	private ResponseEntity<Map<String, String>> createResponseEntity(RuntimeException e, HttpStatus status){
		Map<String, String> error = new HashMap();
		error.put("error-message", e.getMessage());
		return ResponseEntity.status(status).body(error);
	}

	@ExceptionHandler(CustomAuthenticationException.class)
	public ResponseEntity<Map<String, String>> handle(CustomAuthenticationException e){
		return createResponseEntity(e, HttpStatus.UNAUTHORIZED);
	}
	
	
	@ExceptionHandler(UsenameNotFoundException.class)
	public ResponseEntity<?> handlerUsenameNotFound(UsenameNotFoundException e){
		Map<String, String> error = new HashMap();
		error.put("error-message", e.getMessage());
		return ResponseEntity.badRequest().body(error);
	}
	
	
	@ExceptionHandler(MemberInfoDuplicatedException.class) // 회원정보 중복시 에러반환
	public ResponseEntity<?> handlerDuplicateInfo(MemberInfoDuplicatedException e){
		Map<String, String> error = new HashMap();
		error.put("error-message", e.getMessage());
		return ResponseEntity.badRequest().body(error);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class) // Controller - dto에서 Valid로 인해 에러응답할때 어떤 에러인지 정보를 담기위해서
	public ResponseEntity<?> handlerArgumentsNotValid(MethodArgumentNotValidException e){
		Map<String, String> errors = new HashMap();
		e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		return ResponseEntity.badRequest().body(errors);
	}
	
	@ExceptionHandler(InvalidParameterException.class)
	public ResponseEntity<Map<String, String>> handleInvalidParameter(InvalidParameterException e){
		Map<String, String> error = new HashMap();
		error.put("error-message", e.getMessage());
		return ResponseEntity.badRequest().body(error);
	}
	
	@ExceptionHandler(LogoutFailureException.class)
	public ResponseEntity<Map<String, String>> handleLogoutFailure(LogoutFailureException e){
		Map<String, String> error = new HashMap();
		error.put("error-message", e.getMessage());
		return ResponseEntity.badRequest().body(error);
	}
	
	
}
