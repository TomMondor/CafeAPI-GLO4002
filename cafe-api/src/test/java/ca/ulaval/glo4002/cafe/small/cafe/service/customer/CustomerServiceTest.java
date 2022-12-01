package ca.ulaval.glo4002.cafe.small.cafe.service.customer;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.SeatNumber;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerFactory;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerName;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Coffee;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Order;
import ca.ulaval.glo4002.cafe.fixture.BillFixture;
import ca.ulaval.glo4002.cafe.fixture.CustomerFixture;
import ca.ulaval.glo4002.cafe.fixture.OrderFixture;
import ca.ulaval.glo4002.cafe.fixture.SeatFixture;
import ca.ulaval.glo4002.cafe.service.CafeRepository;
import ca.ulaval.glo4002.cafe.service.customer.CustomerService;
import ca.ulaval.glo4002.cafe.service.customer.dto.BillDTO;
import ca.ulaval.glo4002.cafe.service.customer.dto.CustomerDTO;
import ca.ulaval.glo4002.cafe.service.customer.dto.OrderDTO;
import ca.ulaval.glo4002.cafe.service.customer.parameter.CheckInCustomerParams;
import ca.ulaval.glo4002.cafe.service.customer.parameter.CheckOutCustomerParams;
import ca.ulaval.glo4002.cafe.service.customer.parameter.CustomerOrderParams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {
    private static final CustomerName CUSTOMER_NAME = new CustomerName("Bob Bisonette");
    private static final CustomerId CUSTOMER_ID = new CustomerId("ABC273031");
    private static final CheckInCustomerParams CHECK_IN_CUSTOMER_PARAMS_NO_GROUP = new CheckInCustomerParams(CUSTOMER_ID.value(), CUSTOMER_NAME.value(), null);
    private static final CheckOutCustomerParams CHECKOUT_CUSTOMER_PARAMS = new CheckOutCustomerParams(CUSTOMER_ID.value());
    private static final Customer CUSTOMER = new CustomerFixture().withCustomerId(CUSTOMER_ID).withCustomerName(CUSTOMER_NAME).build();
    private static final Seat SEAT_WITH_CUSTOMER = new SeatFixture().withSeatNumber(new SeatNumber(1)).withCustomer(CUSTOMER).build();
    private static final Coffee AN_AMERICANO_COFFEE = new Coffee(CoffeeType.Americano);
    private static final Coffee A_DARK_ROAST_COFFEE = new Coffee(CoffeeType.DarkRoast);
    private static final Order A_ORDER = new OrderFixture().build();
    private static final Bill A_VALID_BILL = new BillFixture().build();

    private CustomerService customersService;
    private CustomerFactory customerFactory;

    private CafeRepository cafeRepository;
    private Cafe mockCafe;

    @BeforeEach
    public void createCustomersService() {
        cafeRepository = mock(CafeRepository.class);
        customerFactory = mock(CustomerFactory.class);
        customersService = new CustomerService(cafeRepository, customerFactory);

        mockCafe = mock(Cafe.class);
        when(cafeRepository.get()).thenReturn(mockCafe);
    }

    @Test
    public void whenGettingCustomer_shouldGetCafe() {
        when(mockCafe.getSeatByCustomerId(CUSTOMER_ID)).thenReturn(SEAT_WITH_CUSTOMER);

        customersService.getCustomer(CUSTOMER_ID);

        verify(cafeRepository).get();
    }

    @Test
    public void whenGettingCustomer_shouldGetCustomerSeat() {
        when(mockCafe.getSeatByCustomerId(CUSTOMER_ID)).thenReturn(SEAT_WITH_CUSTOMER);

        customersService.getCustomer(CUSTOMER_ID);

        verify(mockCafe).getSeatByCustomerId(CUSTOMER_ID);
    }

    @Test
    public void whenGettingCustomer_shouldReturnMatchingCustomerDTO() {
        when(mockCafe.getSeatByCustomerId(CUSTOMER_ID)).thenReturn(SEAT_WITH_CUSTOMER);

        CustomerDTO actualCustomerDTO = customersService.getCustomer(CUSTOMER_ID);

        assertEquals(CUSTOMER_NAME, actualCustomerDTO.name());
        assertEquals(SEAT_WITH_CUSTOMER.getNumber(), actualCustomerDTO.seatNumber());
    }

    @Test
    public void whenCheckingInCustomer_shouldGetCafe() {
        when(customerFactory.createCustomer(CUSTOMER_ID, CUSTOMER_NAME)).thenReturn(CUSTOMER);

        customersService.checkIn(CHECK_IN_CUSTOMER_PARAMS_NO_GROUP);

        verify(cafeRepository).get();
    }

    @Test
    public void whenCheckingInCustomer_shouldCreateNewCustomer() {
        when(customerFactory.createCustomer(CUSTOMER_ID, CUSTOMER_NAME)).thenReturn(CUSTOMER);

        customersService.checkIn(CHECK_IN_CUSTOMER_PARAMS_NO_GROUP);

        verify(customerFactory).createCustomer(CUSTOMER_ID, CUSTOMER_NAME);
    }

    @Test
    public void whenCheckingInCustomer_shouldCheckInInCafe() {
        when(customerFactory.createCustomer(CUSTOMER_ID, CUSTOMER_NAME)).thenReturn(CUSTOMER);

        customersService.checkIn(CHECK_IN_CUSTOMER_PARAMS_NO_GROUP);

        verify(mockCafe).checkIn(CUSTOMER, CHECK_IN_CUSTOMER_PARAMS_NO_GROUP.groupName());
    }

    @Test
    public void whenCheckingInCustomer_shouldUpdateCafe() {
        when(customerFactory.createCustomer(CUSTOMER_ID, CUSTOMER_NAME)).thenReturn(CUSTOMER);

        customersService.checkIn(CHECK_IN_CUSTOMER_PARAMS_NO_GROUP);

        verify(cafeRepository).saveOrUpdate(mockCafe);
    }

    @Test
    public void whenGettingOrders_shouldGetCafe() {
        when(mockCafe.getOrderByCustomerId(CUSTOMER_ID)).thenReturn(A_ORDER);

        customersService.getOrder(CUSTOMER_ID);

        verify(cafeRepository).get();
    }

    @Test
    public void whenGettingOrders_shouldGetOrdersFromCafe() {
        when(mockCafe.getOrderByCustomerId(CUSTOMER_ID)).thenReturn(A_ORDER);

        customersService.getOrder(CUSTOMER_ID);

        verify(mockCafe).getOrderByCustomerId(CUSTOMER_ID);
    }

    @Test
    public void whenGettingOrders_shouldReturnMatchingOrderDTO() {
        Order expectedOrders = new OrderFixture().withItems(List.of(AN_AMERICANO_COFFEE, A_DARK_ROAST_COFFEE)).build();
        when(mockCafe.getOrderByCustomerId(CUSTOMER_ID)).thenReturn(expectedOrders);

        OrderDTO actualOrderDTO = customersService.getOrder(CUSTOMER_ID);

        assertEquals(expectedOrders.items().get(0), actualOrderDTO.coffees().get(0));
        assertEquals(expectedOrders.items().get(1), actualOrderDTO.coffees().get(1));
    }

    @Test
    public void whenCheckingOut_shouldGetCafe() {
        customersService.checkOut(CHECKOUT_CUSTOMER_PARAMS);

        verify(cafeRepository).get();
    }

    @Test
    public void whenCheckingOut_shouldCheckOutWithCustomerId() {
        customersService.checkOut(CHECKOUT_CUSTOMER_PARAMS);

        verify(mockCafe).checkOut(CUSTOMER_ID);
    }

    @Test
    public void whenCheckingOut_shouldUpdateCafe() {
        customersService.checkOut(CHECKOUT_CUSTOMER_PARAMS);

        verify(cafeRepository).saveOrUpdate(mockCafe);
    }

    @Test
    public void whenPlacingOrder_shouldGetCafe() {
        customersService.placeOrder(new CustomerOrderParams(CUSTOMER_ID.value(), List.of(CoffeeType.Americano.toString())));

        verify(cafeRepository).get();
    }

    @Test
    public void whenPlacingOrder_shouldPlaceOrder() {
        customersService.placeOrder(new CustomerOrderParams(CUSTOMER_ID.value(), List.of(CoffeeType.Americano.toString())));

        verify(mockCafe).placeOrder(CUSTOMER_ID, new OrderFixture().withItems(List.of(AN_AMERICANO_COFFEE)).build());
    }

    @Test
    public void whenPlacingOrder_shouldUpdateCafe() {
        customersService.placeOrder(new CustomerOrderParams(CUSTOMER_ID.value(), List.of(CoffeeType.Americano.toString())));

        verify(cafeRepository).saveOrUpdate(mockCafe);
    }

    @Test
    public void givenCheckedOutCustomer_whenGettingBill_shouldGetBillFromCafe() {
        when(mockCafe.getCustomerBill(CUSTOMER_ID)).thenReturn(A_VALID_BILL);
        customersService.getCustomerBill(CUSTOMER_ID);

        verify(mockCafe).getCustomerBill(CUSTOMER_ID);
    }

    @Test
    public void whenGettingBill_shouldGetCafe() {
        when(mockCafe.getCustomerBill(CUSTOMER_ID)).thenReturn(A_VALID_BILL);
        customersService.getCustomerBill(CUSTOMER_ID);

        verify(cafeRepository).get();
    }

    @Test
    public void whenGettingBill_shouldReturnMatchingBillDTO() {
        when(mockCafe.getCustomerBill(CUSTOMER_ID)).thenReturn(A_VALID_BILL);

        BillDTO actualBillDTO = customersService.getCustomerBill(CUSTOMER_ID);

        assertEquals(A_VALID_BILL.order().items(), actualBillDTO.coffees());
        assertEquals(A_VALID_BILL.subtotal(), actualBillDTO.subtotal());
        assertEquals(A_VALID_BILL.taxes(), actualBillDTO.taxes());
        assertEquals(A_VALID_BILL.total(), actualBillDTO.total());
        assertEquals(A_VALID_BILL.tip(), actualBillDTO.tip());
    }
}
