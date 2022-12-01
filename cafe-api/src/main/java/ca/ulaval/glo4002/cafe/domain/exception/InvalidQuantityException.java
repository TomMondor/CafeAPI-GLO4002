package ca.ulaval.glo4002.cafe.domain.exception;

public class InvalidQuantityException extends CafeException {
    public InvalidQuantityException() {
        super("QUANTITY_LOWER_THAN_ZERO", "Quantity cannot be negative.");
    }
}
