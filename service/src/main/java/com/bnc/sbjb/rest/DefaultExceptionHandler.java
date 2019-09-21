package com.bnc.sbjb.rest;

import com.bnc.api.model.error.CustomError;
import com.bnc.api.model.error.ValidationError;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class DefaultExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler({NoSuchElementException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleExceptionNotFound(final Exception ex) {
        logger.debug("Could not find resource", ex);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CustomError handleIllegalArgumentException(final IllegalArgumentException ex) {
        logger.debug("Invalid Argument Received", ex);
        return new CustomError(HttpStatus.BAD_REQUEST, "Invalid Argument Received");
    }

    @ExceptionHandler({
        HttpMessageNotReadableException.class,
        MethodArgumentTypeMismatchException.class,
        HttpMediaTypeNotSupportedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CustomError handleBadRequests(final Exception ex) {
        logger.debug("Bad input from client", ex);
        return new CustomError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CustomError handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        CustomError errors = new CustomError(HttpStatus.BAD_REQUEST, "Validation Error");
        List<ValidationError> subErrors = new ArrayList<>();
        if (ex.getBindingResult().getFieldErrorCount() > 0) {
            List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                subErrors.add(new ValidationError(fieldError.getObjectName().replace("Dto", ""), fieldError.getField(),
                    fieldError.getRejectedValue(), fieldError.getDefaultMessage()));
            }
        }
        errors.setSubErrors(subErrors);
        return errors;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleAccessDenied(AccessDeniedException ex) {
        logger.debug("Access Denied", ex);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public CustomError handleExceptionMethodNotAllowed(final Exception ex) {
        logger.debug("Method not allowed", ex);
        return new CustomError(HttpStatus.METHOD_NOT_ALLOWED, "HTTP Method not allowed");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public CustomError handleException(HttpServletRequest httpServletRequest, Exception ex) {
        logger.error("Unknown exception [queryString=\"{}\" errorMessage=\"{}\"]", httpServletRequest.getQueryString(), ex.getMessage(), ex);
        return new CustomError(HttpStatus.INTERNAL_SERVER_ERROR,
            "Oops something went wrong. Please contact us if this keeps occurring.");
    }
}
