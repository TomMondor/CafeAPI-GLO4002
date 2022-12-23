package ca.ulaval.glo4002.cafe.application.customer;

import ca.ulaval.glo4002.cafe.application.CafeRepository;
import ca.ulaval.glo4002.cafe.application.customer.dto.BillDTO;
import ca.ulaval.glo4002.cafe.application.customer.dto.CustomerDTO;
import ca.ulaval.glo4002.cafe.application.customer.dto.OrderDTO;
import ca.ulaval.glo4002.cafe.application.customer.parameter.CustomerOrderParams;
import ca.ulaval.glo4002.cafe.domain.Cafe;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.sale.bill.Bill;

public class CustomerService {
    private final CafeRepository cafeRepository;

    public CustomerService(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    public CustomerDTO getCustomer(CustomerId customerId) {
        Cafe cafe = cafeRepository.get();
        Seat seat = cafe.findSeatByCustomerId(customerId);

        return CustomerDTO.fromSeat(seat);
    }

    public OrderDTO getOrder(CustomerId customerId) {
        Cafe cafe = cafeRepository.get();

        Order order = cafe.findOrderByCustomerId(customerId);

        return OrderDTO.fromOrder(order);
    }

    public BillDTO getCustomerBill(CustomerId customerId) {
        Cafe cafe = cafeRepository.get();
        Bill bill = cafe.findCustomerBill(customerId);

        return BillDTO.fromBill(bill);
    }

    public void placeOrder(CustomerOrderParams customerOrderParams) {
        Cafe cafe = cafeRepository.get();
        cafe.placeOrder(customerOrderParams.customerId(), customerOrderParams.order());
        cafeRepository.saveOrUpdate(cafe);
    }
}
