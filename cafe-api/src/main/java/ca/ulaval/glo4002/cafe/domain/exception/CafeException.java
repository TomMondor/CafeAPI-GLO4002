package ca.ulaval.glo4002.cafe.domain.exception;

public abstract class CafeException extends RuntimeException {
    private final String error;

    public CafeException(String error, String message) {
        super(message);
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
