package ca.ulaval.glo4002.cafe.domain.exception;

public class InvalidCubeNameException extends CafeException {
    public InvalidCubeNameException() {
        super("INVALID_CUBE_NAME", "The cube name should not be empty.");
    }
}
