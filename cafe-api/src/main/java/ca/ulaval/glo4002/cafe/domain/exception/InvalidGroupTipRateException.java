package ca.ulaval.glo4002.cafe.domain.exception;

public class InvalidGroupTipRateException extends CafeException {
    public InvalidGroupTipRateException() {
        super("INVALID_GROUP_TIP_RATE", "The group tip rate must be set to a value between 0 to 100.");
    }
}
