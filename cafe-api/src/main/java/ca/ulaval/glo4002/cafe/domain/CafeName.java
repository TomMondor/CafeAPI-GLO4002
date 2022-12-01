package ca.ulaval.glo4002.cafe.domain;

public record CafeName(String value) {
    public CafeName {
        if (value.isBlank()) {
            throw new IllegalArgumentException("Cafe name cannot be empty.");
        }
    }
}
