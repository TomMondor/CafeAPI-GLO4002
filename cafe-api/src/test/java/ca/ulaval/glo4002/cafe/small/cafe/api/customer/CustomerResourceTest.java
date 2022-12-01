package ca.ulaval.glo4002.cafe.small.cafe.api.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.api.customer.CustomerResource;
import ca.ulaval.glo4002.cafe.api.customer.request.OrderRequest;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.SeatNumber;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerName;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Order;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupName;
import ca.ulaval.glo4002.cafe.fixture.request.OrderRequestFixture;
import ca.ulaval.glo4002.cafe.service.customer.CustomerService;
import ca.ulaval.glo4002.cafe.service.customer.dto.BillDTO;
import ca.ulaval.glo4002.cafe.service.customer.dto.CustomerDTO;
import ca.ulaval.glo4002.cafe.service.customer.dto.OrderDTO;
import ca.ulaval.glo4002.cafe.service.customer.parameter.CustomerOrderParams;

import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CustomerResourceTest {
    private static final CustomerId CUSTOMER_ID = new CustomerId("customerId");
    private static final CustomerName CUSTOMER_NAME = new CustomerName("customerName");
    private static final GroupName GROUP_NAME = new GroupName("groupName");
    private static final SeatNumber SEAT_NUMBER = new SeatNumber(1);
    private static final Amount AMOUNT = new Amount(0);
    private static final Order ORDER = new Order(new ArrayList<>());
    private static final CustomerDTO A_CUSTOMER_DTO = new CustomerDTO(CUSTOMER_NAME, SEAT_NUMBER, Optional.of(GROUP_NAME));
    private static final BillDTO A_BILL_DTO = new BillDTO(ORDER.items(), AMOUNT, AMOUNT, AMOUNT, AMOUNT);
    private static final OrderDTO A_ORDER_DTO = new OrderDTO(new ArrayList<>());
    private static final List<String> LIST_OF_COFFEE = List.of("Latte");

    private CustomerResource customerResource;
    private CustomerService customerService;

    @BeforeEach
    public void createCustomerResource() {
        customerService = mock(CustomerService.class);
        customerResource = new CustomerResource(customerService);
    }

    @Test
    public void whenGettingCustomer_shouldGetCustomer() {
        when(customerService.getCustomer(CUSTOMER_ID)).thenReturn(A_CUSTOMER_DTO);

        customerResource.getCustomer(CUSTOMER_ID.value());

        verify(customerService).getCustomer(CUSTOMER_ID);
    }

    @Test
    public void givenValidCustomerID_whenGettingCustomer_shouldReturn200() {
        when(customerService.getCustomer(CUSTOMER_ID)).thenReturn(A_CUSTOMER_DTO);

        Response response = customerResource.getCustomer(CUSTOMER_ID.value());

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void whenPuttingOrderForCustomer_shouldPlaceOrderForCustomer() {
        OrderRequest orderRequest = new OrderRequestFixture()
            .withOrders(LIST_OF_COFFEE)
            .build();
        CustomerOrderParams customerOrderParams = new CustomerOrderParams(CUSTOMER_ID.value(), orderRequest.orders);

        customerResource.putOrderForCustomer(CUSTOMER_ID.value(), orderRequest);

        verify(customerService).placeOrder(customerOrderParams);
    }

    @Test
    public void givenValidRequestAndValidCustomerID_whenPuttingOrderForCustomer_shouldReturn200() {
        OrderRequest orderRequest = new OrderRequestFixture()
            .withOrders(LIST_OF_COFFEE)
            .build();

        Response response = customerResource.putOrderForCustomer(CUSTOMER_ID.value(), orderRequest);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void whenGettingCustomerBill_shouldGetCustomerBill() {
        when(customerService.getCustomerBill(CUSTOMER_ID)).thenReturn(A_BILL_DTO);

        customerResource.getCustomerBill(CUSTOMER_ID.value());

        verify(customerService).getCustomerBill(CUSTOMER_ID);
    }

    @Test
    public void givenValidCustomerID_whenGettingBill_shouldReturn200() {
        when(customerService.getCustomerBill(CUSTOMER_ID)).thenReturn(A_BILL_DTO);

        Response response = customerResource.getCustomerBill(CUSTOMER_ID.value());

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void whenGettingOrders_shouldGetOrders() {
        when(customerService.getOrder(CUSTOMER_ID)).thenReturn(A_ORDER_DTO);

        customerResource.getOrders(CUSTOMER_ID.value());

        verify(customerService).getOrder(CUSTOMER_ID);
    }

    @Test
    public void givenValidCustomerID_whenGettingOrders_shouldReturn200() {
        when(customerService.getOrder(CUSTOMER_ID)).thenReturn(A_ORDER_DTO);

        Response response = customerResource.getOrders(CUSTOMER_ID.value());

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}
