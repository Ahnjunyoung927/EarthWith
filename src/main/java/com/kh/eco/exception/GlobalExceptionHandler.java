package com.kh.eco.exception;

import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kh.eco.common.responseData.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	/*
	private ResponseEntity<Map<String, String>> createResponseEntity(RuntimeException e, HttpStatus status){
		Map<String, String> error = new HashMap();
		error.put("message", e.getMessage());
		return ResponseEntity.status(status).body(error);
	}
	*/

	@ExceptionHandler(CustomAuthenticationException.class)
	public ResponseEntity<ErrorResponse<String>> handle(CustomAuthenticationException e, HttpServletRequest request){
		return ErrorResponse.badRequest(e.getMessage(), request.getRequestURI());
	}
	
	
	@ExceptionHandler(UsenameNotFoundException.class) // 
	public ResponseEntity<ErrorResponse<String>> handlerUsenameNotFound(UsenameNotFoundException e, HttpServletRequest request){
		return ErrorResponse.badRequest(e.getMessage(), request.getRequestURI());
	}
	
	
	@ExceptionHandler(MemberInfoDuplicatedException.class) // 회원정보 중복시 에러반환
	public ResponseEntity<ErrorResponse<String>> handlerDuplicateInfo(MemberInfoDuplicatedException e, HttpServletRequest request){
		return ErrorResponse.badRequest(e.getMessage(), request.getRequestURI());
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class) // Controller - dto에서 Valid로 인해 에러응답할때 어떤 에러인지 정보를 담기위해서
	public ResponseEntity<ErrorResponse<String>> handlerArgumentsNotValid(MethodArgumentNotValidException e, HttpServletRequest request){
		return ErrorResponse.badRequest(e.getMessage(), request.getRequestURI());
	}
	
	@ExceptionHandler(InvalidParameterException.class)
	public ResponseEntity<ErrorResponse<String>> handleInvalidParameter(InvalidParameterException e, HttpServletRequest request){
		return ErrorResponse.badRequest(e.getMessage(), request.getRequestURI());
	}
	
	@ExceptionHandler(LogoutFailureException.class)
	public ResponseEntity<ErrorResponse<String>> handleLogoutFailure(LogoutFailureException e, HttpServletRequest request){
		return ErrorResponse.badRequest(e.getMessage(), request.getRequestURI());
	}
	
	@ExceptionHandler(PageNotFoundException.class)
	public ResponseEntity<ErrorResponse<String>> handlePageNotFound(PageNotFoundException e, HttpServletRequest request){
		return ErrorResponse.badRequest(e.getMessage(), request.getRequestURI());
	}
	
	@ExceptionHandler(DeleteFailureException.class)
	public ResponseEntity<ErrorResponse<String>> handleDeleteFail(DeleteFailureException e, HttpServletRequest request){
		return ErrorResponse.badRequest(e.getMessage(), request.getRequestURI());
	}
	
	@ExceptionHandler(RestoreFailureException.class)
	public ResponseEntity<ErrorResponse<String>> handleRestoreFail(RestoreFailureException e, HttpServletRequest request){
		return ErrorResponse.badRequest(e.getMessage(), request.getRequestURI());
	}
	
	@ExceptionHandler(FindFailureException.class)
	public ResponseEntity<ErrorResponse<String>> handleFindFail(FindFailureException e, HttpServletRequest request){
	    return ErrorResponse.badRequest(e.getMessage(), request.getRequestURI());
	}
	
	@ExceptionHandler(SQLResponseException.class)
	public ResponseEntity<ErrorResponse<String>> handleSQLResponse(SQLResponseException e, HttpServletRequest request){
		return ErrorResponse.badRequest(e.getMessage(), request.getRequestURI());
	}
	
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<ErrorResponse<String>> handleSQLResponse(SQLException e, HttpServletRequest request){
		return ErrorResponse.badRequest("서버에 문제가 발생했습니다.", request.getRequestURI());
	}
	
	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<ErrorResponse<String>> handleSQLResponse(DataAccessException e, HttpServletRequest request){
		return ErrorResponse.badRequest("서버에 문제가 발생했습니다.", request.getRequestURI());
	}

	
}
