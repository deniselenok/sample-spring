package delenok.task.rest;

import delenok.task.exceptions.BasicException;
import delenok.task.exceptions.MessageCode;
import delenok.task.exceptions.NotExistException;
import delenok.task.exceptions.ValidationBasicException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    public RestExceptionHandler() {
        log.info("Creating default REST exceptions handler...");
    }

    private static ResponseEntity<Map> prepareResponseEntity(MessageCode messageCode, Exception ex, final HttpServletRequest request) {
        return prepareBasicResponseEntity(messageCode, ex, request, ex.getMessage());
    }

    private static ResponseEntity<Map> prepareBasicResponseEntity(MessageCode messageCode, Exception ex, final HttpServletRequest request, String message) {
        log.error("Exception message", ex);
        log.error("Requested url {}", request.getRequestURL());

        Map<String, String> map = new HashMap<>();
        map.put("code", messageCode.getCode());
        map.put("message", message);
        map.put("path", request.getServletPath());

        return new ResponseEntity<Map>(map, messageCode.getStatus());
    }

    @ExceptionHandler(value = {BasicException.class})
    protected ResponseEntity<Map> handleBasicException(final Exception ex, final HttpServletRequest request) {
        return prepareResponseEntity(MessageCode.ERR_GENERAL_BASIC_EXCEPTION, ex, request);
    }

    @ExceptionHandler(value = {NotExistException.class})
    protected ResponseEntity<Map> handleNotExistException(final Exception ex, final HttpServletRequest request) {
        return prepareResponseEntity(MessageCode.ERR_VALIDATION_ENTITY_NOT_FOUND, ex, request);
    }

    @ExceptionHandler(value = {ValidationBasicException.class})
    protected ResponseEntity<Map> handleValidationBasicException(final Exception ex, final HttpServletRequest request) {
        return prepareResponseEntity(MessageCode.ERR_VALIDATION_FAILED, ex, request);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    protected ResponseEntity<Map> handleHttpMessageNotReadableException(final Exception ex, final HttpServletRequest request) {
        return prepareBasicResponseEntity(MessageCode.ERR_REQUEST_BODY_IS_MISSING,
                ex,
                request,
                "Required request body is missing");
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    protected ResponseEntity<Map> handleNotSupportedHttpMethodException(final Exception ex, final HttpServletRequest request) {
        return prepareResponseEntity(MessageCode.ERR_NOT_SUPPORTED_HTTP_METHOD, ex, request);
    }

    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
    protected ResponseEntity<Map> handleHttpMediaTypeNotSupportedMethodException(final Exception ex, final HttpServletRequest request) {
        return prepareResponseEntity(MessageCode.ERR_NOT_SUPPORTED_MEDIA_TYPE, ex, request);
    }

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    protected ResponseEntity<Map> handleException(final Exception ex, final HttpServletRequest request) {
        return prepareBasicResponseEntity(MessageCode.ERR_GENERAL_NOT_WRAPPED_EXCEPTION,
                ex,
                request,
                "Something went wrong =)");
    }
}
