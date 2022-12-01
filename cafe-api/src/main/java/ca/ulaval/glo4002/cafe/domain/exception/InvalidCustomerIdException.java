package ca.ulaval.glo4002.cafe.domain.exception;

public class InvalidCustomerIdException extends CafeException {
    public InvalidCustomerIdException() {
        super("INVALID_CUSTOMER_ID", " The customer_id must be a non-empty string.");
    }
}
