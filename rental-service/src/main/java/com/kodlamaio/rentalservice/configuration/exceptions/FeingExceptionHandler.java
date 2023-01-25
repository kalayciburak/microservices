package com.kodlamaio.rentalservice.configuration.exceptions;

import com.kodlamaio.common.configuration.exceptions.RestExceptionHandler;
import com.kodlamaio.common.constants.Messages;
import com.kodlamaio.common.utils.results.ErrorDataResult;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FeingExceptionHandler extends RestExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ErrorDataResult<Object> handleFeignBadRequestException(FeignException.BadRequest exception) {
        ErrorDataResult<Object> errorDataResult = new ErrorDataResult<>(exception.getMessage(),
                Messages.Exception.Feign);

        return errorDataResult;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    public ErrorDataResult<Object> handleFeignUnauthorizedException(FeignException.Unauthorized exception) {
        ErrorDataResult<Object> errorDataResult = new ErrorDataResult<>(exception.getMessage(),
                Messages.Exception.Feign);

        return errorDataResult;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN) // 403
    public ErrorDataResult<Object> handleFeignForbiddenException(FeignException.Forbidden exception) {
        ErrorDataResult<Object> errorDataResult = new ErrorDataResult<>(exception.getMessage(),
                Messages.Exception.Feign);

        return errorDataResult;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public ErrorDataResult<Object> handleFeignNotFoundException(FeignException.NotFound exception) {
        ErrorDataResult<Object> errorDataResult = new ErrorDataResult<>(exception.getMessage(),
                Messages.Exception.Feign);

        return errorDataResult;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED) // 405
    public ErrorDataResult<Object> handleFeignMethodNotAllowedException(FeignException.MethodNotAllowed exception) {
        ErrorDataResult<Object> errorDataResult = new ErrorDataResult<>(exception.getMessage(),
                Messages.Exception.Feign);

        return errorDataResult;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // 422
    public ErrorDataResult<Object> handleFeignUnprocessableEntityException(FeignException.UnprocessableEntity exception) {
        ErrorDataResult<Object> errorDataResult = new ErrorDataResult<>(exception.getMessage(),
                Messages.Exception.Feign);

        return errorDataResult;
    }
}
