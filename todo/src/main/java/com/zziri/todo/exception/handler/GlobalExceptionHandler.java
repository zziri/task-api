package com.zziri.todo.exception.handler;

import com.zziri.todo.domain.Response;
import com.zziri.todo.exception.custom.AccountSigninFailedException;
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
        return Response.<ErrorDto>builder()
                .data(ErrorDto.of(500, "An unknown server error has occurred")).build();
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<ErrorDto> handleUserNotFoundException(UserNotFoundException ex) {
        return Response.<ErrorDto>builder()
                .data(ErrorDto.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred because user data could not be found")).build();
    }

    @ExceptionHandler(value = AccountSigninFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<ErrorDto> handleAccountSigninFailedException(AccountSigninFailedException ex) {
        return Response.<ErrorDto>builder()
                .data(ErrorDto.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred because account signin failed")).build();
    }
}
