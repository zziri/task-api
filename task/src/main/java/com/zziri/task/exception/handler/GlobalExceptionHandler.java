package com.zziri.task.exception.handler;

import com.zziri.task.domain.Response;
import com.zziri.task.exception.custom.*;
import com.zziri.task.exception.dto.ErrorDto;
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
        ex.printStackTrace();
        return Response.<ErrorDto>builder().error(true)
                .data(ErrorDto.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unknown server error has occurred")).build();
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response<ErrorDto> handleUserNotFoundException(UserNotFoundException ex) {
        return Response.<ErrorDto>builder().error(true)
                .data(ErrorDto.of(HttpStatus.NOT_FOUND.value(), ex.getMessage())).build();
    }

    @ExceptionHandler(value = AccountSigninFailedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response<ErrorDto> handleAccountSigninFailedException(AccountSigninFailedException ex) {
        return Response.<ErrorDto>builder().error(true)
                .data(ErrorDto.of(HttpStatus.UNAUTHORIZED.value(), ex.getMessage())).build();
    }

    @ExceptionHandler(value = AuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response<ErrorDto> handleAuthenticationEntryPointException(AuthenticationEntryPointException ex) {
        return Response.<ErrorDto>builder().error(true)
                .data(ErrorDto.of(HttpStatus.UNAUTHORIZED.value(), ex.getMessage())).build();
    }

    @ExceptionHandler(value = CommunicationException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public Response<ErrorDto> handleCommunicationException(CommunicationException ex) {
        return Response.<ErrorDto>builder().error(true)
                .data(ErrorDto.of(HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage())).build();
    }

    @ExceptionHandler(value = UserExistException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Response<ErrorDto> handleUserExistException(UserExistException ex) {
        return Response.<ErrorDto>builder().error(true)
                .data(ErrorDto.of(HttpStatus.NO_CONTENT.value(), ex.getMessage())).build();
    }

    @ExceptionHandler(value = TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response<ErrorDto> handleTaskNotFoundException(TaskNotFoundException ex) {
        return Response.<ErrorDto>builder().error(true)
                .data(ErrorDto.of(HttpStatus.NOT_FOUND.value(), ex.getMessage())).build();
    }
}
