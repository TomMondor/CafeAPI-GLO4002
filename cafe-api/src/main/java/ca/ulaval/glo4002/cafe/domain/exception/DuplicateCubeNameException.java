package ca.ulaval.glo4002.cafe.domain.exception;

public class DuplicateCubeNameException extends CafeException {
    public DuplicateCubeNameException() {
        super("DUPLICATE_CUBE_NAME", "One of the cube names is already used.");
    }
}
