package ca.ulaval.glo4002.cafe.application.customer;

import ca.ulaval.glo4002.cafe.application.CafeRepository;
import ca.ulaval.glo4002.cafe.application.customer.dto.BillDTO;
import ca.ulaval.glo4002.cafe.application.customer.dto.CustomerDTO;
import ca.ulaval.glo4002.cafe.application.customer.dto.OrderDTO;
import ca.ulaval.glo4002.cafe.application.customer.parameter.CustomerOrderParams;
import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Order;

public class CustomerService {
    private final CafeRepository cafeRepository;

    public CustomerService(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    public CustomerDTO getCustomer(CustomerId customerId) {
        Cafe cafe = cafeRepository.get();
        Seat seat = cafe.getSeatByCustomerId(customerId);

        return CustomerDTO.fromSeat(seat);
    }

    public OrderDTO getOrder(CustomerId customerId) {
        Cafe cafe = cafeRepository.get();

        Order order = cafe.getOrderByCustomerId(customerId);

        return OrderDTO.fromOrder(order);
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
