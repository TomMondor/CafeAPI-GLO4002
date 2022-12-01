package ca.ulaval.glo4002.cafe.domain.exception;

public class IngredientTypeMismatchException extends CafeException {
    public IngredientTypeMismatchException() {
        super("INGREDIENT_TYPE_MISMATCH", "Cannot operate on ingredients of different types.");
    }
}
