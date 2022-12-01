package ca.ulaval.glo4002.cafe.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigurationRequest {
    @NotNull(message = "The group_reservation_method may not be null.")
    public String group_reservation_method;

    @NotNull(message = "The organization_name may not be null.")
    public String organization_name;

    @NotNull(message = "The country may not be null.")
    public String country;

    @NotNull(message = "The province may not be null.")
    public String province;

    @NotNull(message = "The state may not be null.")
    public String state;

    @NotNull(message = "The cube_size may not be null.")
    public int cube_size;

    @NotNull(message = "The group_tip_rate may not be null.")
    public float group_tip_rate;
}
