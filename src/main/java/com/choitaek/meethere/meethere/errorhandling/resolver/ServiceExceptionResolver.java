package com.choitaek.meethere.meethere.errorhandling.resolver;

import com.choitaek.meethere.meethere.dto.common.response.ResponseErrorDto;
import com.choitaek.meethere.meethere.errorhandling.exception.service.DuplicateErrorException;
import com.choitaek.meethere.meethere.errorhandling.exception.service.LoginErrorException;
import com.choitaek.meethere.meethere.errorhandling.exception.service.RegisterErrorException;
import com.choitaek.meethere.meethere.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class ServiceExceptionResolver {

    private final ResponseUtil responseUtil;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseErrorDto<?> handle(MethodArgumentNotValidException e, HttpServletRequest request) {
        return responseUtil.buildErrorResponse(HttpStatus.BAD_REQUEST, e.getFieldError().getDefaultMessage(), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = LoginErrorException.class)
    public ResponseErrorDto<?> handle(LoginErrorException e, HttpServletRequest request) {
        return responseUtil.buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = DuplicateErrorException.class)
    public ResponseErrorDto<?> handle(DuplicateErrorException e, HttpServletRequest request) {
        return responseUtil.buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RegisterErrorException.class)
    public ResponseErrorDto<?> handle(RegisterErrorException e, HttpServletRequest request) {
        return responseUtil.buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), request.getRequestURI());
    }
}
