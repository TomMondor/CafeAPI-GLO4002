package ca.ulaval.glo4002.cafe.fixture.request;

import ca.ulaval.glo4002.cafe.api.request.CheckInRequest;

public class CheckInRequestFixture {
    private String customerId = "SOME_ID";
    private String customerName = "SOME NAME";
    private String groupName;

    public CheckInRequestFixture withCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public CheckInRequestFixture withCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public CheckInRequestFixture withGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public CheckInRequest build() {
        CheckInRequest checkInRequest = new CheckInRequest();
        checkInRequest.group_name = groupName;
        checkInRequest.customer_id = customerId;
        checkInRequest.customer_name = customerName;
        return checkInRequest;
    }
}
