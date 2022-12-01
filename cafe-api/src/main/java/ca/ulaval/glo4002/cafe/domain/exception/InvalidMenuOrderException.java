package ca.ulaval.glo4002.cafe.domain.exception;

public class InvalidMenuOrderException extends CafeException {
    public InvalidMenuOrderException() {
        super("INVALID_MENU_ORDER", "An item ordered is not on the menu.");
    }
}
