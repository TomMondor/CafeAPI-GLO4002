package ca.ulaval.glo4002.cafe.domain.location;

import java.util.Optional;

import ca.ulaval.glo4002.cafe.domain.exception.InvalidConfigurationCountryException;

public record Location(Country country, Optional<Province> province, Optional<State> state) {
    public Location(Country country, Optional<Province> province, Optional<State> state) {
        switch (country) {
            case CA -> {
                if (province.isEmpty()) {
                    throw new InvalidConfigurationCountryException();
                }
                this.country = country;
                this.province = province;
                this.state = Optional.empty();
            }
            case US -> {
                if (state.isEmpty()) {
                    throw new InvalidConfigurationCountryException();
                }
                this.country = country;
                this.province = Optional.empty();
                this.state = state;
            }
            default -> {
                this.country = country;
                this.province = Optional.empty();
                this.state = Optional.empty();
            }
        }
    }

    public static Location fromDetails(String countryString, String provinceString, String stateString) {
        Country country = Country.fromString(countryString);
        Optional<Province> province = Optional.empty();
        Optional<State> state = Optional.empty();

        switch (country) {
            case CA -> province = Optional.of(Province.fromString(provinceString));
            case US -> state = Optional.of(State.fromString(stateString));
        }

        return new Location(country, province, state);
    }
}
