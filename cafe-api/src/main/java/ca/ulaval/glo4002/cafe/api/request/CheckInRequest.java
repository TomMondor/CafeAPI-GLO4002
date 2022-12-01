package ca.ulaval.glo4002.cafe.api.request;

import jakarta.validation.constraints.NotNull;

public class CheckInRequest {
    @NotNull(message = "The customer_id may not be null.")
    public String customer_id;

    @NotNull(message = "The customer_name may not be null.")
    public String customer_name;

    public String group_name;
}
