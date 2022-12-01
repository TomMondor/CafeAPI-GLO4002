package ca.ulaval.glo4002.cafe.domain.exception;

public class InsufficientIngredientsException extends CafeException {
    public InsufficientIngredientsException() {
        super("INSUFFICIENT_INGREDIENTS", "We lack the necessary number of ingredients to fulfill your order.");
    }
}
