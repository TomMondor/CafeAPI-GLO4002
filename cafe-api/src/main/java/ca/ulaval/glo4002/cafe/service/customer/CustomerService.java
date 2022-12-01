package ca.ulaval.glo4002.cafe.service.customer;

import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerFactory;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Order;
import ca.ulaval.glo4002.cafe.service.CafeRepository;
import ca.ulaval.glo4002.cafe.service.customer.dto.BillDTO;
import ca.ulaval.glo4002.cafe.service.customer.dto.CustomerDTO;
import ca.ulaval.glo4002.cafe.service.customer.dto.OrderDTO;
import ca.ulaval.glo4002.cafe.service.customer.parameter.CheckInCustomerParams;
import ca.ulaval.glo4002.cafe.service.customer.parameter.CheckOutCustomerParams;
import ca.ulaval.glo4002.cafe.service.customer.parameter.CustomerOrderParams;

public class CustomerService {
    private final CafeRepository cafeRepository;
    private final CustomerFactory customerFactory;

    public CustomerService(CafeRepository cafeRepository, CustomerFactory customerFactory) {
        this.cafeRepository = cafeRepository;
        this.customerFactory = customerFactory;
    }

    public CustomerDTO getCustomer(CustomerId customerId) {
        Cafe cafe = cafeRepository.get();
        Seat seat = cafe.getSeatByCustomerId(customerId);

        return CustomerDTO.fromSeat(seat);
    }

    public void checkIn(CheckInCustomerParams checkInCustomerParams) {
        Cafe cafe = cafeRepository.get();

        Customer customer = customerFactory.createCustomer(checkInCustomerParams.customerId(), checkInCustomerParams.customerName());
        cafe.checkIn(customer, checkInCustomerParams.groupName());

        cafeRepository.saveOrUpdate(cafe);
    }

    public OrderDTO getOrder(CustomerId customerId) {
        Cafe cafe = cafeRepository.get();

        Order order = cafe.getOrderByCustomerId(customerId);

        return OrderDTO.fromOrder(order);
    }

    public void checkOut(CheckOutCustomerParams checkOutCustomerParams) {
        Cafe cafe = cafeRepository.get();
        cafe.checkOut(checkOutCustomerParams.customerId());
        cafeRepository.saveOrUpdate(cafe);
    }

    public BillDTO getCustomerBill(CustomerId customerId) {
        Cafe cafe = cafeRepository.get();
        Bill bill = cafe.getCustomerBill(customerId);

        return BillDTO.fromBill(bill);
    }

    public void placeOrder(CustomerOrderParams customerOrderParams) {
        Cafe cafe = cafeRepository.get();
        cafe.placeOrder(customerOrderParams.customerId(), customerOrderParams.order());
        cafeRepository.saveOrUpdate(cafe);
    }
}
