package uzumtech.notification.jnotificationservice.exception;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import uzumtech.notification.jnotificationservice.constant.enums.ErrorType;

@Getter
@ToString
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationException extends RuntimeException {

    int code;
    String message;
    HttpStatus status;
    ErrorType errorType;

    public ApplicationException(int code, String message, ErrorType errorType, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
        this.message = message;
        this.errorType = errorType;
    }
}
