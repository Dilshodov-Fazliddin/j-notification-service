package uzumtech.notification.jnotificationservice.exception;

public class NotificationNotFoundException extends RuntimeException{

    public NotificationNotFoundException(Long id){
        super("Notification с id " + id + " не найден");
    }
}
