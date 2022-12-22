package ca.ulaval.glo4002.cafe.medium;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.CafeFactory;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.SeatNumber;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Amount;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerFactory;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerName;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Order;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.PendingOrder;
import ca.ulaval.glo4002.cafe.domain.menu.CoffeeName;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationFactory;
import ca.ulaval.glo4002.cafe.fixture.CoffeeFixture;
import ca.ulaval.glo4002.cafe.fixture.OrderFixture;
import ca.ulaval.glo4002.cafe.infrastructure.InMemoryCafeRepository;
import ca.ulaval.glo4002.cafe.service.CafeRepository;
import ca.ulaval.glo4002.cafe.service.CafeService;
import ca.ulaval.glo4002.cafe.service.customer.CustomerService;
import ca.ulaval.glo4002.cafe.service.customer.dto.BillDTO;
import ca.ulaval.glo4002.cafe.service.customer.dto.CustomerDTO;
import ca.ulaval.glo4002.cafe.service.customer.dto.OrderDTO;
import ca.ulaval.glo4002.cafe.service.customer.parameter.CheckInCustomerParams;
import ca.ulaval.glo4002.cafe.service.customer.parameter.CheckOutCustomerParams;
import ca.ulaval.glo4002.cafe.service.customer.parameter.CustomerOrderParams;
import ca.ulaval.glo4002.cafe.service.inventory.InventoryService;
import ca.ulaval.glo4002.cafe.service.parameter.IngredientsParams;
import ca.ulaval.glo4002.cafe.service.registration.RegistrationService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerServiceTest {
    private static final CustomerId A_CUSTOMER_ID = new CustomerId("123");
    private static final String A_CUSTOMER_NAME = "Joe";
    private static final CheckInCustomerParams CHECK_IN_CUSTOMER_PARAMS = new CheckInCustomerParams(A_CUSTOMER_ID.value(), A_CUSTOMER_NAME, null);
    private static final CheckOutCustomerParams CHECK_OUT_CUSTOMER_PARAMS = new CheckOutCustomerParams(A_CUSTOMER_ID.value());
    private static final CustomerOrderParams CUSTOMER_ORDER_PARAMS =
        new CustomerOrderParams(A_CUSTOMER_ID, new PendingOrder(List.of(new CoffeeName("Americano"))));
    private static final Order CUSTOMER_ORDER = new Order(List.of(new CoffeeFixture().withAmericano().build()));
    private static final IngredientsParams INGREDIENT_PARAMS = new IngredientsParams(100, 100, 100, 100);

    CustomerService customerService;
    CafeRepository cafeRepository;
    CafeService cafeService;
    InventoryService inventoryService;
    RegistrationService registrationService;

    @BeforeEach
    public void instanciateAttributes() {
        cafeRepository = new InMemoryCafeRepository();
        customerService = new CustomerService(cafeRepository);
        registrationService = new RegistrationService(cafeRepository, new ReservationFactory(), new CustomerFactory());
        cafeService = new CafeService(cafeRepository);
        inventoryService = new InventoryService(cafeRepository);
        Cafe cafe = new CafeFactory().createCafe(List.of(new CoffeeFixture().withAmericano().build()));
        cafeRepository.saveOrUpdate(cafe);
    }

    @Test
    public void givenSavedCustomer_whenPlacingOrder_shouldSaveOrderForCustomer() {
        Order expectedOrder = new OrderFixture().build();
        registrationService.checkIn(CHECK_IN_CUSTOMER_PARAMS);
        inventoryService.addIngredientsToInventory(INGREDIENT_PARAMS);

        customerService.placeOrder(CUSTOMER_ORDER_PARAMS);
        OrderDTO actualOrder = customerService.getOrder(A_CUSTOMER_ID);

        assertEquals(OrderDTO.fromOrder(expectedOrder), actualOrder);
    }

    @Test
    public void givenSavedCustomer_whenGettingCustomer_shouldReturnValidCustomerDTO() {
        CustomerDTO expectedCustomerDTO = new CustomerDTO(new CustomerName(A_CUSTOMER_NAME), new SeatNumber(1), Optional.empty());
        registrationService.checkIn(CHECK_IN_CUSTOMER_PARAMS);

        CustomerDTO actualCustomerDTO = customerService.getCustomer(A_CUSTOMER_ID);

        assertEquals(expectedCustomerDTO, actualCustomerDTO);
    }

    @Test
    public void givenSavedCustomerWithOrder_whenGettingOrder_shouldReturnValidOrderDTO() {
        OrderDTO expectedOrderDTO = new OrderDTO(CUSTOMER_ORDER.items());
        registrationService.checkIn(CHECK_IN_CUSTOMER_PARAMS);
        inventoryService.addIngredientsToInventory(INGREDIENT_PARAMS);
        customerService.placeOrder(CUSTOMER_ORDER_PARAMS);

        OrderDTO actualOrderDTO = customerService.getOrder(CHECK_IN_CUSTOMER_PARAMS.customerId());

        assertEquals(expectedOrderDTO, actualOrderDTO);
    }

    @Test
    public void givenSavedBill_whenGettingCustomerBill_shouldReturnValidBillDTO() {
        BillDTO expectedBillDTO = new BillDTO(CUSTOMER_ORDER.items(), new Amount(0),
            new Amount(2.25f), new Amount(0), new Amount(2.25f));
        registrationService.checkIn(CHECK_IN_CUSTOMER_PARAMS);
        inventoryService.addIngredientsToInventory(INGREDIENT_PARAMS);
        customerService.placeOrder(CUSTOMER_ORDER_PARAMS);
        registrationService.checkOut(CHECK_OUT_CUSTOMER_PARAMS);

        BillDTO actualBillDTO = customerService.getCustomerBill(CHECK_IN_CUSTOMER_PARAMS.customerId());

        assertEquals(expectedBillDTO, actualBillDTO);
    }
}
