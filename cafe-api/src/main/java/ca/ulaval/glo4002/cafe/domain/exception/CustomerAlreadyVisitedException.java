package ca.ulaval.glo4002.cafe.domain.exception;

public class CustomerAlreadyVisitedException extends CafeException {
    public CustomerAlreadyVisitedException() {
        super("DUPLICATE_CUSTOMER_ID", "The customer cannot visit the café multiple times in the same day.");
    }
}
