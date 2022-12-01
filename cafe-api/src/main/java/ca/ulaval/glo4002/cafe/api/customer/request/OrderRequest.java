package ca.ulaval.glo4002.cafe.api.customer.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public class OrderRequest {
    @NotNull(message = "The orders may not be null.")
    public List<String> orders;
}
