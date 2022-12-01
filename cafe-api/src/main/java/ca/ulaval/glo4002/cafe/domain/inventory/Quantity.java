package ca.ulaval.glo4002.cafe.domain.inventory;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidQuantityException;

public record Quantity(int value) {
    public Quantity {
        if (value < 0) {
            throw new InvalidQuantityException();
        }
    }

    public Quantity add(Quantity quantity) {
        return new Quantity(this.value() + quantity.value());
    }

    public Quantity remove(Quantity quantity) {
        return new Quantity(this.value() - quantity.value());
    }

    public boolean isGreaterThan(Quantity quantity) {
        return this.value() > quantity.value();
    }
}
