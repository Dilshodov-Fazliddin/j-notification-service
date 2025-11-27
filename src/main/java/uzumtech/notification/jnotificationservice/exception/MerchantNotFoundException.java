package uzumtech.notification.jnotificationservice.exception;

public class MerchantNotFoundException extends RuntimeException{

    public MerchantNotFoundException(Long id){
        super("Merchant с id " + id + " не найден");
    }
}
