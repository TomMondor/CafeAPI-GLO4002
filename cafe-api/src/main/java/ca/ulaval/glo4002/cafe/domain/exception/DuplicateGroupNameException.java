package ca.ulaval.glo4002.cafe.domain.exception;

public class DuplicateGroupNameException extends CafeException {
    public DuplicateGroupNameException() {
        super("DUPLICATE_GROUP_NAME", "The specified group already made a reservation today.");
    }
}
