package ca.ulaval.glo4002.cafe.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ca.ulaval.glo4002.cafe.domain.exception.CustomerAlreadyVisitedException;
import ca.ulaval.glo4002.cafe.domain.exception.CustomerNotFoundException;
import ca.ulaval.glo4002.cafe.domain.exception.DuplicateGroupNameException;
import ca.ulaval.glo4002.cafe.domain.exception.NoReservationsFoundException;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.layout.Layout;
import ca.ulaval.glo4002.cafe.domain.layout.LayoutFactory;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeName;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeSize;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.menu.Coffee;
import ca.ulaval.glo4002.cafe.domain.menu.Menu;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.order.PendingOrder;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupName;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategyFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.strategies.ReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.sale.PointOfSale;
import ca.ulaval.glo4002.cafe.domain.sale.bill.Bill;

public class Cafe {
    private final ReservationStrategyFactory reservationStrategyFactory;
    private final Layout layout;
    private final List<Reservation> reservations = new ArrayList<>();
    private final Inventory inventory;
    private final PointOfSale pointOfSale;
    private final Menu menu;
    private CubeSize cubeSize;
    private CafeName cafeName;
    private Location location;
    private ReservationStrategy reservationStrategy;

    public Cafe(List<CubeName> cubeNames, CafeConfiguration cafeConfiguration, Menu menu) {
        reservationStrategyFactory = new ReservationStrategyFactory();

        LayoutFactory layoutFactory = new LayoutFactory();
        this.layout = layoutFactory.createLayout(cafeConfiguration.cubeSize(), cubeNames);

        this.inventory = new Inventory();
        this.pointOfSale = new PointOfSale();
        this.menu = menu;

        updateConfiguration(cafeConfiguration);
    }

    public void updateConfiguration(CafeConfiguration cafeConfiguration) {
        this.cubeSize = cafeConfiguration.cubeSize();
        this.cafeName = cafeConfiguration.cafeName();
        this.reservationStrategy = reservationStrategyFactory.createReservationStrategy(cafeConfiguration.reservationType());
        this.pointOfSale.updateGroupTipRate(cafeConfiguration.groupTipRate());
        this.location = cafeConfiguration.location();
    }

    public CafeName getName() {
        return cafeName;
    }

    public Layout getLayout() {
        return layout;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void checkIn(Customer customer, Optional<GroupName> groupName) {
        checkIfCustomerAlreadyVisitedToday(customer.getId());
        assignSeatToCustomer(customer, groupName);
        pointOfSale.openBillForCustomer(customer.getId());
    }

    public Seat getSeatByCustomerId(CustomerId customerId) {
        return layout.getSeatByCustomerId(customerId);
    }

    public void addMenuItem(Coffee newMenuItem) {
        menu.addMenuItem(newMenuItem);
    }

    public Order findOrderByCustomerId(CustomerId customerId) {
        return pointOfSale.findOrderByCustomerId(customerId);
    }

    public void makeReservation(Reservation reservation) {
        checkIfGroupNameAlreadyExists(reservation.name());
        reservationStrategy.makeReservation(reservation, layout.getCubes());
        reservations.add(reservation);
    }

    public void close() {
        layout.reset(cubeSize);
        reservations.clear();
        inventory.clear();
        menu.clearCustomMenuItems();
        pointOfSale.clearOrdersAndBills();
    }

    public void checkOut(CustomerId customerId) {
        boolean isGroupMember = layout.getSeatByCustomerId(customerId).isReservedForGroup();
        pointOfSale.produceBill(customerId, location, isGroupMember);
        layout.checkout(customerId);
    }

    public void placeOrder(CustomerId customerId, PendingOrder order) {
        Order approvedOrder = menu.approveOrder(order);
        if (!layout.isCustomerAlreadySeated(customerId)) {
            throw new CustomerNotFoundException();
        }
        inventory.useIngredients(approvedOrder.ingredientsNeeded());
        pointOfSale.saveOrder(customerId, approvedOrder);
    }

    public void addIngredientsToInventory(List<Ingredient> ingredients) {
        inventory.add(ingredients);
    }

    public Bill getCustomerBill(CustomerId customerId) {
        return pointOfSale.findBillByCustomerId(customerId);
    }

    private void checkIfCustomerAlreadyVisitedToday(CustomerId customerId) {
        if (pointOfSale.hasProducedBill(customerId) || layout.isCustomerAlreadySeated(customerId)) {
            throw new CustomerAlreadyVisitedException();
        }
    }

    private void assignSeatToCustomer(Customer customer, Optional<GroupName> groupName) {
        if (groupName.isPresent()) {
            validateHasReservation(groupName.get());
            layout.assignSeatToGroupMember(customer, groupName.get());
        } else {
            layout.assignSeatToIndividual(customer);
        }
    }

    private void validateHasReservation(GroupName groupName) {
        for (Reservation reservation : reservations) {
            if (reservation.name().equals(groupName)) {
                return;
            }
        }
        throw new NoReservationsFoundException();
    }

    private void checkIfGroupNameAlreadyExists(GroupName name) {
        if (reservations.stream().map(Reservation::name).toList().contains(name)) {
            throw new DuplicateGroupNameException();
        }
    }
}
