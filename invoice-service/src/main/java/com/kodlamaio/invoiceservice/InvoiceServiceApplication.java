package com.kodlamaio.invoiceservice;

import com.kodlamaio.common.constants.Messages;
import com.kodlamaio.common.util.exceptions.BusinessException;
import com.kodlamaio.common.util.mapping.ModelMapperManager;
import com.kodlamaio.common.util.mapping.ModelMapperService;
import com.kodlamaio.common.util.results.ErrorDataResult;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableDiscoveryClient
@RestControllerAdvice
public class InvoiceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvoiceServiceApplication.class, args);
    }

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ModelMapperService getModelMapperService(ModelMapper mapper) {
        return new ModelMapperManager(mapper);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ErrorDataResult<>(validationErrors, Messages.Exception.Validation);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleValidationException(ValidationException exception) {
        return new ErrorDataResult<>(exception.getMessage(), Messages.Exception.Validation);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleBusinessException(BusinessException exception) {
        ErrorDataResult<Object> errorDataResult = new ErrorDataResult<>(exception.getMessage(),
                Messages.Exception.Business);

        return errorDataResult;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        ErrorDataResult<Object> errorDataResult = new ErrorDataResult<>(exception.getMessage(),
                                                                        Messages.Exception.DataIntegrityViolation);

        return errorDataResult;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleRuntimeEception(RuntimeException exception) {
        ErrorDataResult<Object> errorDataResult = new ErrorDataResult<>(exception.getMessage(),
                Messages.Exception.Runtime);

        return errorDataResult;
    }
}