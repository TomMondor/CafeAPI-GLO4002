package ca.ulaval.glo4002.cafe.domain.location;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidConfigurationCountryException;

public enum State {
    AL,
    AZ,
    CA,
    FL,
    ME,
    NY,
    TX;

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
}
