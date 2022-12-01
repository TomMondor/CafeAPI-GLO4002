package ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Amount(float value) {
    public Amount add(Amount amount) {
        return new Amount(value + amount.value);
    }

    public float getRoundedValue() {
        BigDecimal rounded = new BigDecimal(Float.toString(value)).setScale(2, RoundingMode.CEILING);
        return rounded.floatValue();
    }
}
