package ca.ulaval.glo4002.cafe.domain.exception;

public class InvalidGroupSizeException extends CafeException {
    public InvalidGroupSizeException() {
        super("INVALID_GROUP_SIZE", "Groups must reserve at least two seats.");
    }
}
