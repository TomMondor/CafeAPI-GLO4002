package ca.ulaval.glo4002.cafe.domain.exception;

public class InvalidCustomerNameException extends CafeException {
    public InvalidCustomerNameException() {
        super("INVALID_CUSTOMER_NAME", "The customer_name cannot be null or empty.");
    }
}
