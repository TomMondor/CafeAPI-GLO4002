package ca.ulaval.glo4002.cafe.domain.exception;

public class InvalidCubeSizeException extends CafeException {
    public InvalidCubeSizeException() {
        super("INVALID_CUBE_SIZE", "The cube size should be greater than 0.");
    }
}
