package ca.ulaval.glo4002.cafe.fixture.request;

import java.util.List;

import ca.ulaval.glo4002.cafe.api.customer.request.OrderRequest;

public class OrderRequestFixture {
    private List<String> orders = List.of();

    public OrderRequestFixture withOrders(List<String> orders) {
        this.orders = orders;
        return this;
    }

    public OrderRequest build() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.orders = orders;
        return orderRequest;
    }
}
