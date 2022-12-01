package ca.ulaval.glo4002.cafe.domain.exception;

public class InvalidGroupNameException extends CafeException {
    public InvalidGroupNameException() {
        super("INVALID_GROUP_NAME", "The group_name cannot be null or empty.");
    }
}
