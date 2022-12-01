package ca.ulaval.glo4002.cafe.domain.exception;

public class NoGroupSeatsException extends CafeException {
    public NoGroupSeatsException() {
        super("NO_GROUP_SEATS", "There are no more seats reserved for that group.");
    }
}
