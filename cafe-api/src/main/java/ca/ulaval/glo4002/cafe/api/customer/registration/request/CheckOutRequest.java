package ca.ulaval.glo4002.cafe.api.customer.registration.request;

import jakarta.validation.constraints.NotNull;

public class CheckOutRequest {
    @NotNull(message = "The customer_id may not be null.")
    public String customer_id;
}
