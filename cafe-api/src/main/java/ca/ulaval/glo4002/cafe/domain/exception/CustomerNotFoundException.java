package ca.ulaval.glo4002.cafe.domain.exception;

public class CustomerNotFoundException extends CafeException {
    public CustomerNotFoundException() {
        super("INVALID_CUSTOMER_ID", "The customer does not exist.");
    }
}
