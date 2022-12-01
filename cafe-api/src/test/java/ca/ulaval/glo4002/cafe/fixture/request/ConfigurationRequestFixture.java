package ca.ulaval.glo4002.cafe.fixture.request;

import ca.ulaval.glo4002.cafe.api.request.ConfigurationRequest;

public class ConfigurationRequestFixture {
    public String group_reservation_method = "Default";
    public String organization_name = "Les 4-Fées";
    public String country = "CA";
    public String province = "QC";
    public String state = "";
    public int cube_size = 4;
    public float group_tip_rate;

    public ConfigurationRequestFixture withGroupReservationMethod(String groupReservationMethod) {
        this.group_reservation_method = groupReservationMethod;
        return this;
    }

    public ConfigurationRequestFixture withOrganizationName(String organizationName) {
        this.organization_name = organizationName;
        return this;
    }

    public ConfigurationRequestFixture withCubeSize(int cubeSize) {
        this.cube_size = cubeSize;
        return this;
    }

    public ConfigurationRequestFixture withCountry(String country) {
        this.country = country;
        return this;
    }

    public ConfigurationRequestFixture withProvince(String province) {
        this.province = province;
        return this;
    }

    public ConfigurationRequestFixture withState(String state) {
        this.state = state;
        return this;
    }

    public ConfigurationRequestFixture withTipRate(float tipRate) {
        this.group_tip_rate = tipRate;
        return this;
    }

    public ConfigurationRequest build() {
        ConfigurationRequest configurationRequest = new ConfigurationRequest();
        configurationRequest.group_reservation_method = group_reservation_method;
        configurationRequest.organization_name = organization_name;
        configurationRequest.cube_size = cube_size;
        configurationRequest.country = country;
        configurationRequest.state = state;
        configurationRequest.province = province;
        configurationRequest.group_tip_rate = group_tip_rate;
        return configurationRequest;
    }
}
