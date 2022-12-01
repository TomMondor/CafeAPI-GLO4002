package ca.ulaval.glo4002.cafe.small.cafe.api.customer.assembler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.api.customer.assembler.CustomerResponseAssembler;
import ca.ulaval.glo4002.cafe.api.customer.response.CustomerResponse;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.SeatNumber;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerName;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupName;
import ca.ulaval.glo4002.cafe.fixture.CustomerFixture;
import ca.ulaval.glo4002.cafe.fixture.SeatFixture;
import ca.ulaval.glo4002.cafe.service.customer.dto.CustomerDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CustomerResponseAssemblerTest {
    private static final CustomerName CUSTOMER_NAME = new CustomerName("Keanu Reeves");
    private static final CustomerId CUSTOMER_ID = new CustomerId("abd4bced-4fce-44a0-aa41-c41c5777e679");
    private static final GroupName GROUP_NAME = new GroupName("Rise Against the Machine");
    private static final SeatNumber SEAT_NUMBER = new SeatNumber(1);

    private CustomerResponseAssembler customerResponseAssembler;

    @BeforeEach
    public void createAssembler() {
        customerResponseAssembler = new CustomerResponseAssembler();
    }

    @Test
    public void givenCustomerWithNoGroupDTO_whenAssemblingCustomerResponse_shouldReturnCustomerResponseWithNullGroupName() {
        Customer customer = new CustomerFixture().withCustomerName(CUSTOMER_NAME).withCustomerId(CUSTOMER_ID).build();
        Seat seat = new SeatFixture().withSeatNumber(SEAT_NUMBER).withCustomer(customer).build();
        CustomerDTO customerDTO = CustomerDTO.fromSeat(seat);

        CustomerResponse actualCustomerResponse = customerResponseAssembler.toCustomerResponse(customerDTO);

        assertEquals(CUSTOMER_NAME.value(), actualCustomerResponse.name());
        assertEquals(SEAT_NUMBER.value(), actualCustomerResponse.seat_number());
        assertNull(actualCustomerResponse.group_name());
    }

    @Test
    public void givenCustomerWithGroupDTO_whenAssemblingCustomerResponse_shouldReturnCustomerResponseWithGroupName() {
        Customer customer = new CustomerFixture().withCustomerName(CUSTOMER_NAME).withCustomerId(CUSTOMER_ID).build();
        Seat seat = new SeatFixture().withSeatNumber(SEAT_NUMBER).build();
        seat.reserveForGroup(GROUP_NAME);
        seat.sitCustomer(customer);
        CustomerDTO customerDTO = CustomerDTO.fromSeat(seat);

        CustomerResponse actualCustomerResponse = customerResponseAssembler.toCustomerResponse(customerDTO);

        assertEquals(CUSTOMER_NAME.value(), actualCustomerResponse.name());
        assertEquals(SEAT_NUMBER.value(), actualCustomerResponse.seat_number());
        assertEquals(GROUP_NAME.value(), actualCustomerResponse.group_name());
    }
}
