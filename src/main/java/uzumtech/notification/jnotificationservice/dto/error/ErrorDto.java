package uzumtech.notification.jnotificationservice.dto.error;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uzumtech.notification.jnotificationservice.constant.enums.ErrorType;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorDto {
    int code;
    String message;
    ErrorType type;
    List<String>validationErrors;
}
