package ca.ulaval.glo4002.cafe.api.customer.registration.request;

import jakarta.validation.constraints.NotNull;

public class ReservationRequest {
    @NotNull(message = "The group_name may not be null.")
    public String group_name;

    @NotNull(message = "The group_size may not be null.")
    public int group_size;
}
