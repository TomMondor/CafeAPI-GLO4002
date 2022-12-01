package ca.ulaval.glo4002.cafe.domain;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidConfigurationCountryException;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Tax;

public enum State {
    AL(new Tax(0.04f)),
    AZ(new Tax(0.056f)),
    CA(new Tax(0.0725f)),
    FL(new Tax(0.06f)),
    ME(new Tax(0.055f)),
    NY(new Tax(0.04f)),
    TX(new Tax(0.0625f));

    private final Tax tax;

    State(Tax tax) {
        this.tax = tax;
    }

    public static State fromString(String state) {
        if (State.contains(state)) {
            return State.valueOf(state);
        }
        throw new InvalidConfigurationCountryException();
    }

    private static boolean contains(String other) {
        for (State state : State.values()) {
            if (state.name().equals(other)) {
                return true;
            }
        }
        return false;
    }

    public Tax getTax() {
        return tax;
    }
}
