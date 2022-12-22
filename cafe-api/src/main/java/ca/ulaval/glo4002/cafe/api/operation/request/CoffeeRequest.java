package ca.ulaval.glo4002.cafe.api.operation.request;

import ca.ulaval.glo4002.cafe.api.inventory.request.InventoryRequest;

import jakarta.validation.constraints.NotNull;

public class CoffeeRequest {
    @NotNull(message = "The cost may not be null.")
    public float cost;

    @NotNull(message = "The name may not be null.")
    public String name;

    @NotNull(message = "The ingredients may not be null.")
    public InventoryRequest ingredients;
}
