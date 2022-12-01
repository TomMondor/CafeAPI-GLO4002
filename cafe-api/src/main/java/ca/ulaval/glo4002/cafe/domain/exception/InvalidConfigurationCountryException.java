package ca.ulaval.glo4002.cafe.domain.exception;

public class InvalidConfigurationCountryException extends CafeException {
    public InvalidConfigurationCountryException() {
        super("INVALID_COUNTRY", "The specified country is invalid.");
    }
}
