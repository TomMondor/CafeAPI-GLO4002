package ca.ulaval.glo4002.cafe.api.request;

import jakarta.validation.constraints.NotNull;

public class InventoryRequest {
    @NotNull(message = "The Chocolate quantity may not be null")
    public int Chocolate;

    @NotNull(message = "The Espresso quantity may not be null")
    public int Espresso;

    @NotNull(message = "The Milk quantity may not be null")
    public int Milk;

    @NotNull(message = "The Water quantity may not be null")
    public int Water;
}
