package ca.ulaval.glo4002.cafe.domain.exception;

public class InsufficientSeatsException extends CafeException {
    public InsufficientSeatsException() {
        super("INSUFFICIENT_SEATS", "There are currently no available seats. Please come back later.");
    }
}
