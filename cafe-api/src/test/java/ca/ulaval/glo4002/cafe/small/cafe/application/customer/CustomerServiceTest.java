package ca.ulaval.glo4002.cafe.small.cafe.application.customer;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.application.CafeRepository;
import ca.ulaval.glo4002.cafe.application.customer.CustomerService;
import ca.ulaval.glo4002.cafe.application.customer.dto.BillDTO;
import ca.ulaval.glo4002.cafe.application.customer.dto.CustomerDTO;
import ca.ulaval.glo4002.cafe.application.customer.dto.OrderDTO;
import ca.ulaval.glo4002.cafe.application.customer.parameter.CustomerOrderParams;
import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.SeatNumber;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerName;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Order;
import ca.ulaval.glo4002.cafe.domain.menu.Coffee;
import ca.ulaval.glo4002.cafe.domain.menu.CoffeeName;
import ca.ulaval.glo4002.cafe.fixture.BillFixture;
import ca.ulaval.glo4002.cafe.fixture.CoffeeFixture;
import ca.ulaval.glo4002.cafe.fixture.CustomerFixture;
import ca.ulaval.glo4002.cafe.fixture.OrderFixture;
import ca.ulaval.glo4002.cafe.fixture.PendingOrderFixture;
import ca.ulaval.glo4002.cafe.fixture.SeatFixture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {
    private static final CustomerName CUSTOMER_NAME = new CustomerName("Bob Bisonette");
    private static final CustomerId CUSTOMER_ID = new CustomerId("ABC273031");
    private static final Customer CUSTOMER = new CustomerFixture().withCustomerId(CUSTOMER_ID).withCustomerName(CUSTOMER_NAME).build();
    private static final Seat SEAT_WITH_CUSTOMER = new SeatFixture().withSeatNumber(new SeatNumber(1)).withCustomer(CUSTOMER).build();
    private static final Coffee AN_AMERICANO_COFFEE = new CoffeeFixture().withAmericano().build();
    private static final Coffee A_DARK_ROAST_COFFEE = new CoffeeFixture().withDarkRoast().build();
    private static final Order A_ORDER = new OrderFixture().build();
    private static final Bill A_VALID_BILL = new BillFixture().build();
    private static final String A_COFFEE_NAME = "Americano";
    private static final List<String> SOME_COFFEE_NAMES = List.of("Americano", "Dark Roast");

    private CustomerService customersService;

    private CafeRepository cafeRepository;
    private Cafe mockCafe;

    @BeforeEach
    public void createCustomersService() {
        cafeRepository = mock(CafeRepository.class);
        customersService = new CustomerService(cafeRepository);

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
    public void whenPlacingOrder_shouldGetCafe() {
        customersService.placeOrder(new CustomerOrderParams(CUSTOMER_ID.value(), SOME_COFFEE_NAMES));

        verify(cafeRepository).get();
    }

    @Test
    public void whenPlacingOrder_shouldPlaceOrder() {
        customersService.placeOrder(new CustomerOrderParams(CUSTOMER_ID.value(), List.of(A_COFFEE_NAME)));

        verify(mockCafe).placeOrder(CUSTOMER_ID, new PendingOrderFixture().withItems(List.of(new CoffeeName(A_COFFEE_NAME))).build());
    }

    @Test
    public void whenPlacingOrder_shouldUpdateCafe() {
        customersService.placeOrder(new CustomerOrderParams(CUSTOMER_ID.value(), List.of("Some coffee name")));

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
