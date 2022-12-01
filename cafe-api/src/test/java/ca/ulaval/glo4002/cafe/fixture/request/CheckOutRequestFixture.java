package ca.ulaval.glo4002.cafe.fixture.request;

import ca.ulaval.glo4002.cafe.api.request.CheckOutRequest;

public class CheckOutRequestFixture {
    private String customerId = "SOME_ID";

    public CheckOutRequestFixture withCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public CheckOutRequest build() {
        CheckOutRequest checkOutRequest = new CheckOutRequest();
        checkOutRequest.customer_id = customerId;
        return checkOutRequest;
    }
}
