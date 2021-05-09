package com.zziri.todo.exception.handler;

import com.zziri.todo.domain.Response;
import com.zziri.todo.exception.custom.AccountSigninFailedException;
import com.zziri.todo.exception.custom.AuthenticationEntryPointException;
import com.zziri.todo.exception.custom.UserNotFoundException;
import com.zziri.todo.exception.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<ErrorDto> handleRuntimeException(RuntimeException ex) {
        return Response.<ErrorDto>builder().error(true)
                .data(ErrorDto.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unknown server error has occurred")).build();
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<ErrorDto> handleUserNotFoundException(UserNotFoundException ex) {
        return Response.<ErrorDto>builder().error(true)
                .data(ErrorDto.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), UserNotFoundException.MESSAGE)).build();
    }

    @ExceptionHandler(value = AccountSigninFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<ErrorDto> handleAccountSigninFailedException(AccountSigninFailedException ex) {
        return Response.<ErrorDto>builder().error(true)
                .data(ErrorDto.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), AccountSigninFailedException.MESSAGE)).build();
    }

    @ExceptionHandler(value = AuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response<ErrorDto> handleAuthenticationEntryPointException(AuthenticationEntryPointException ex) {
        return Response.<ErrorDto>builder().error(true)
                .data(ErrorDto.of(HttpStatus.UNAUTHORIZED.value(), AuthenticationEntryPointException.MESSAGE)).build();
    }
}
