package ca.ulaval.glo4002.cafe.domain.sale;

import java.util.HashMap;
import java.util.List;

import ca.ulaval.glo4002.cafe.domain.TipRate;
import ca.ulaval.glo4002.cafe.domain.exception.CustomerNoBillException;
import ca.ulaval.glo4002.cafe.domain.exception.CustomerNotFoundException;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.location.Location;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.sale.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.sale.bill.BillFactory;

public class PointOfSale {
    private final HashMap<CustomerId, Bill> bills = new HashMap<>();
    private final HashMap<CustomerId, Order> orders = new HashMap<>();
    private final BillFactory billFactory;
    private TipRate groupTipRate = new TipRate(0);

    public PointOfSale(BillFactory billFactory) {
        this.billFactory = billFactory;
    }

    public void updateGroupTipRate(TipRate groupTipRate) {
        this.groupTipRate = groupTipRate;
    }

    public void openBillForCustomer(CustomerId customerId) {
        orders.put(customerId, new Order(List.of()));
    }

    public void saveOrder(CustomerId customerId, Order order) {
        Order previousOrder = orders.get(customerId);
        Order combinedOrder = previousOrder.addAll(order);
        orders.put(customerId, combinedOrder);
    }

    public Order findOrderByCustomerId(CustomerId customerId) {
        if (!orders.containsKey(customerId)) {
            throw new CustomerNotFoundException();
        }
        return orders.get(customerId);
    }

    public boolean hasOpenBill(CustomerId customerId) {
        return orders.containsKey(customerId);
    }

    public void produceBill(CustomerId customerId, Location location, boolean isGroupMember) {
        Order order = orders.get(customerId);
        orders.remove(customerId);
        Bill bill = billFactory.createBill(order, location, groupTipRate, isGroupMember);
        bills.put(customerId, bill);
    }

    public Bill findBillByCustomerId(CustomerId customerId) {
        if (!hasProducedBill(customerId)) {
            if (hasOpenBill(customerId)) {
                throw new CustomerNoBillException();
            } else {
                throw new CustomerNotFoundException();
            }
        }
        return bills.get(customerId);
    }

    public boolean hasProducedBill(CustomerId customerId) {
        return bills.containsKey(customerId);
    }

    public void clearOrdersAndBills() {
        orders.clear();
        bills.clear();
    }
}
