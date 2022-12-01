package ca.ulaval.glo4002.cafe.domain.exception;

public class CustomerNoBillException extends CafeException {
    public CustomerNoBillException() {
        super("NO_BILL", "The customer needs to do a checkout before receiving his bill.");
    }
}
