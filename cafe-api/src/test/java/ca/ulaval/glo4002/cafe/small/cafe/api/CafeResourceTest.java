package ca.ulaval.glo4002.cafe.small.cafe.api;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.api.CafeResource;
import ca.ulaval.glo4002.cafe.api.request.CheckInRequest;
import ca.ulaval.glo4002.cafe.api.request.CheckOutRequest;
import ca.ulaval.glo4002.cafe.api.request.ConfigurationRequest;
import ca.ulaval.glo4002.cafe.api.request.InventoryRequest;
import ca.ulaval.glo4002.cafe.domain.CafeName;
import ca.ulaval.glo4002.cafe.fixture.request.CheckInRequestFixture;
import ca.ulaval.glo4002.cafe.fixture.request.CheckOutRequestFixture;
import ca.ulaval.glo4002.cafe.fixture.request.ConfigurationRequestFixture;
import ca.ulaval.glo4002.cafe.fixture.request.InventoryRequestFixture;
import ca.ulaval.glo4002.cafe.service.CafeService;
import ca.ulaval.glo4002.cafe.service.customer.CustomerService;
import ca.ulaval.glo4002.cafe.service.customer.parameter.CheckInCustomerParams;
import ca.ulaval.glo4002.cafe.service.customer.parameter.CheckOutCustomerParams;
import ca.ulaval.glo4002.cafe.service.dto.InventoryDTO;
import ca.ulaval.glo4002.cafe.service.dto.LayoutDTO;
import ca.ulaval.glo4002.cafe.service.parameter.ConfigurationParams;
import ca.ulaval.glo4002.cafe.service.parameter.IngredientsParams;

import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CafeResourceTest {
    private static final CafeName A_CAFE_NAME = new CafeName("Bob");
    private static final LayoutDTO A_LAYOUT_DTO = new LayoutDTO(A_CAFE_NAME, new ArrayList<>());
    private static final InventoryDTO A_INVENTORY_DTO = new InventoryDTO(new HashMap<>());
    private static final String CUSTOMER_ID = "customerId";
    private static final String CUSTOMER_NAME = "Bob";
    private static final String GROUP_NAME = "team";
    private static final int CUBE_SIZE = 1;
    private static final String ORGANISATION_NAME = "Bob";
    private static final String GROUP_RESERVATION_METHOD = "Default";
    private static final String COUNTRY = "CA";
    private static final String PROVINCE = "QC";
    private static final String STATE = "";
    private static final int GROUP_TIP_RATE = 0;
    private static final int CHOCOLATE = 1;
    private static final int ESPRESSO = 1;
    private static final int MILK = 1;
    private static final int WATER = 1;

    private CafeService cafeService;
    private CustomerService customerService;
    private CafeResource cafeResource;

    @BeforeEach
    public void createCafeResource() {
        cafeService = mock(CafeService.class);
        customerService = mock(CustomerService.class);
        cafeResource = new CafeResource(cafeService, customerService);
    }

    @Test
    public void whenGettingLayout_shouldGetLayout() {
        when(cafeService.getLayout()).thenReturn(A_LAYOUT_DTO);

        cafeResource.layout();

        verify(cafeService).getLayout();
    }

    @Test
    public void whenGettingLayout_shouldReturn200() {
        when(cafeService.getLayout()).thenReturn(A_LAYOUT_DTO);

        Response response = cafeResource.layout();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void whenClosing_shouldCloseCafe() {
        cafeResource.close();

        verify(cafeService).closeCafe();
    }

    @Test
    public void whenClosing_shouldReturn200() {
        Response response = cafeResource.close();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void whenCheckingIn_shouldCheckInCustomer() {
        CheckInRequest checkInRequest = new CheckInRequestFixture()
            .withCustomerId(CUSTOMER_ID)
            .withCustomerName(CUSTOMER_NAME)
            .withGroupName(GROUP_NAME)
            .build();
        CheckInCustomerParams checkInCustomerParams = new CheckInCustomerParams(CUSTOMER_ID, CUSTOMER_NAME, GROUP_NAME);

        cafeResource.checkIn(checkInRequest);

        verify(customerService).checkIn(checkInCustomerParams);
    }

    @Test
    public void givenValidRequest_whenCheckingIn_shouldReturn201() {
        CheckInRequest checkInRequest = new CheckInRequestFixture()
            .withCustomerId(CUSTOMER_ID)
            .withCustomerName(CUSTOMER_NAME)
            .withGroupName(GROUP_NAME)
            .build();

        Response response = cafeResource.checkIn(checkInRequest);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void whenCheckingIn_shouldReturnPathToCustomerInLocation() {
        CheckInRequest checkInRequest = new CheckInRequestFixture()
            .withCustomerId(CUSTOMER_ID)
            .withCustomerName(CUSTOMER_NAME)
            .withGroupName(GROUP_NAME)
            .build();

        Response response = cafeResource.checkIn(checkInRequest);

        assertTrue(response.getLocation().toString().contains("/customers/" + CUSTOMER_ID));
    }

    @Test
    public void whenCheckingOut_shouldCheckOutCustomer() {
        CheckOutRequest checkOutRequest = new CheckOutRequestFixture()
            .withCustomerId(CUSTOMER_ID)
            .build();
        CheckOutCustomerParams checkOutCustomerParams = new CheckOutCustomerParams(CUSTOMER_ID);

        cafeResource.checkOut(checkOutRequest);

        verify(customerService).checkOut(checkOutCustomerParams);
    }

    @Test
    public void givenValidRequest_whenCheckingOut_shouldReturn201() {
        CheckOutRequest checkOutRequest = new CheckOutRequestFixture()
            .withCustomerId(CUSTOMER_ID)
            .build();

        Response response = cafeResource.checkOut(checkOutRequest);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void whenCheckingOut_shouldReturnPathToBillInLocation() {
        CheckOutRequest checkOutRequest = new CheckOutRequestFixture()
            .withCustomerId(CUSTOMER_ID)
            .build();

        Response response = cafeResource.checkOut(checkOutRequest);

        assertTrue(response.getLocation().toString().contains("/customers/" + CUSTOMER_ID + "/bill"));
    }

    @Test
    public void whenUpdatingConfiguration_shouldUpdateConfiguration() {
        ConfigurationRequest configurationRequest = new ConfigurationRequestFixture()
            .withCubeSize(CUBE_SIZE)
            .withOrganizationName(ORGANISATION_NAME)
            .withGroupReservationMethod(GROUP_RESERVATION_METHOD)
            .withCountry(COUNTRY)
            .withProvince(PROVINCE)
            .withState(STATE)
            .withTipRate(GROUP_TIP_RATE)
            .build();
        ConfigurationParams configurationParams =
            new ConfigurationParams(CUBE_SIZE, ORGANISATION_NAME, GROUP_RESERVATION_METHOD, COUNTRY, PROVINCE, STATE, GROUP_TIP_RATE);

        cafeResource.updateConfiguration(configurationRequest);

        verify(cafeService).updateConfiguration(configurationParams);
    }

    @Test
    public void givenValidRequest_whenUpdatingConfiguration_shouldReturn200() {
        ConfigurationRequest configurationRequest = new ConfigurationRequestFixture()
            .withCubeSize(CUBE_SIZE)
            .withOrganizationName(ORGANISATION_NAME)
            .withGroupReservationMethod(GROUP_RESERVATION_METHOD)
            .withCountry(COUNTRY)
            .withProvince(PROVINCE)
            .withState(STATE)
            .withTipRate(GROUP_TIP_RATE)
            .build();

        Response response = cafeResource.updateConfiguration(configurationRequest);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenValidRequest_whenAddingIngredients_shouldAddIngredientsToInventory() {
        InventoryRequest inventoryRequest = new InventoryRequestFixture()
            .withChocolate(CHOCOLATE)
            .withMilk(MILK)
            .withEspresso(ESPRESSO)
            .withWater(WATER)
            .build();
        IngredientsParams ingredientsParams = new IngredientsParams(CHOCOLATE, MILK, WATER, ESPRESSO);

        cafeResource.putInventory(inventoryRequest);

        verify(cafeService).addIngredientsToInventory(ingredientsParams);
    }

    @Test
    public void givenValidRequest_whenAddingIngredients_shouldReturn200() {
        InventoryRequest inventoryRequest = new InventoryRequestFixture()
            .withChocolate(CHOCOLATE)
            .withMilk(MILK)
            .withEspresso(ESPRESSO)
            .withWater(WATER)
            .build();

        Response response = cafeResource.putInventory(inventoryRequest);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void whenGettingInventory_shouldGetInventory() {
        when(cafeService.getInventory()).thenReturn(A_INVENTORY_DTO);

        cafeResource.getInventory();

        verify(cafeService).getInventory();
    }

    @Test
    public void whenGettingInventory_shouldReturn200() {
        when(cafeService.getInventory()).thenReturn(A_INVENTORY_DTO);

        Response response = cafeResource.getInventory();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}
