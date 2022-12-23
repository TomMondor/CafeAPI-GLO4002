package ca.ulaval.glo4002.cafe.domain.exception;

public class DuplicateMenuItemNameException extends CafeException {
    public DuplicateMenuItemNameException() {
        super("DUPLICATE_MENU_ITEM_NAME", "A menu item with this name already exists.");
    }
}
