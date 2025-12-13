package uzumtech.notification.jnotificationservice.exception;

public class MerchantNotFoundException extends RuntimeException{

    public MerchantNotFoundException(Long id){
        super("Merchant with id " + id + " not found");
    }
}
