package ca.ulaval.glo4002.cafe.domain.exception;

public class InvalidSeatNumberException extends CafeException {
    public InvalidSeatNumberException() {
        super("INVALID_SEAT_NUMBER", "The seat number must be 1 or higher.");
    }
}
