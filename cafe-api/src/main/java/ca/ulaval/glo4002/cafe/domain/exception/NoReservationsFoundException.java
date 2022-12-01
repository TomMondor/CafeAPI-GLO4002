package ca.ulaval.glo4002.cafe.domain.exception;

public class NoReservationsFoundException extends CafeException {
    public NoReservationsFoundException() {
        super("NO_RESERVATIONS_FOUND", "No reservations were made today for that group.");
    }
}
