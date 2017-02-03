package delenok.task.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum MessageCode {

    //ERRORS
    ERR_VALIDATION_FAILED("ERR-1001", HttpStatus.BAD_REQUEST),
    ERR_VALIDATION_ENTITY_NOT_FOUND("ERR-1003", HttpStatus.NOT_FOUND),

    ERR_REQUEST_BODY_IS_MISSING("ERR-1002", HttpStatus.BAD_REQUEST),
    ERR_NOT_SUPPORTED_HTTP_METHOD("ERR-1900", HttpStatus.METHOD_NOT_ALLOWED),
    ERR_NOT_SUPPORTED_MEDIA_TYPE("ERR-1901", HttpStatus.UNSUPPORTED_MEDIA_TYPE),

    // GENERAL
    ERR_GENERAL_BASIC_EXCEPTION("ERR-9997", HttpStatus.INTERNAL_SERVER_ERROR),
    ERR_GENERAL_NOT_WRAPPED_EXCEPTION("ERR-9999", HttpStatus.INTERNAL_SERVER_ERROR);

    @Getter
    private String code;
    @Getter
    private HttpStatus status;


}
