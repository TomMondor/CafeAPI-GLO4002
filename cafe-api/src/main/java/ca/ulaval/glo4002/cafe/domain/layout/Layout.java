package ca.ulaval.glo4002.cafe.domain.layout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import ca.ulaval.glo4002.cafe.domain.Location;
import ca.ulaval.glo4002.cafe.domain.TipRate;
import ca.ulaval.glo4002.cafe.domain.exception.CustomerNotFoundException;
import ca.ulaval.glo4002.cafe.domain.exception.DuplicateCubeNameException;
import ca.ulaval.glo4002.cafe.domain.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.exception.NoGroupSeatsException;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.layout.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeFactory;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeName;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeSize;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.SeatNumber;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Order;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupName;

public class Layout {
    private final CubeFactory cubeFactory;
    private final List<Cube> cubes = new ArrayList<>();

    public Layout(CubeSize cubeSize, List<CubeName> cubeNames) {
        this.cubeFactory = new CubeFactory();
        this.cubes.addAll(createCubes(cubeSize, cubeNames));
    }

    public List<Cube> getCubes() {
        return cubes;
    }

    public Seat getSeatByCustomerId(CustomerId customerId) {
        return getSeatsFromCubes().stream().filter(Seat::isCurrentlyOccupied).filter(seat -> customerId.equals(seat.getCustomer().get().getId())).findFirst()
            .orElseThrow(CustomerNotFoundException::new);
    }

    public Order getOrderByCustomerId(CustomerId customerId) {
        Seat seatWithCustomer =
            getSeatsFromCubes().stream().filter(Seat::isCurrentlyOccupied).filter(seat -> customerId.equals(seat.getCustomer().get().getId())).findFirst()
                .orElseThrow(CustomerNotFoundException::new);
        return seatWithCustomer.getCustomer().get().getOrder();
    }

    public void assignSeatToIndividual(Customer customer) {
        for (Cube cube : cubes) {
            Optional<Seat> firstAvailableSeat = cube.getFirstAvailableSeat();
            if (firstAvailableSeat.isPresent()) {
                firstAvailableSeat.get().sitCustomer(customer);
                return;
            }
        }
        throw new InsufficientSeatsException();
    }

    public void assignSeatToGroupMember(Customer customer, GroupName groupName) {
        for (Cube cube : cubes) {
            Optional<Seat> firstAvailableSeat = cube.getFirstReservedSeatForGroup(groupName);
            if (firstAvailableSeat.isPresent()) {
                firstAvailableSeat.get().sitCustomer(customer);
                return;
            }
        }
        throw new NoGroupSeatsException();
    }

    public void reset(CubeSize cubeSize) {
        List<CubeName> cubeNames = cubes.stream().map(Cube::getName).toList();
        cubes.clear();
        cubes.addAll(createCubes(cubeSize, cubeNames));
    }

    private List<Cube> createCubes(CubeSize cubeSize, List<CubeName> cubeNames) {
        List<Cube> createdCubes = new ArrayList<>();
        int firstSeatNumber = 1;
        checkForDuplicateCubeName(cubeNames);
        for (CubeName cubeName : sortCubeNamesAlphabetically(cubeNames)) {
            Cube cube = this.cubeFactory.createCube(new SeatNumber(firstSeatNumber), cubeName, cubeSize);
            createdCubes.add(cube);
            firstSeatNumber += cube.getNumberOfSeats();
        }
        return createdCubes;
    }

    private List<CubeName> sortCubeNamesAlphabetically(List<CubeName> cubeNames) {
        return cubeNames.stream().sorted().toList();
    }

    private void checkForDuplicateCubeName(List<CubeName> cubeNames) {
        Set<CubeName> cubeNamesInSet = new HashSet<>(cubeNames);
        if (cubeNamesInSet.size() != cubeNames.size()) {
            throw new DuplicateCubeNameException();
        }
    }

    private List<Seat> getSeatsFromCubes() {
        return cubes.stream().map(Cube::getSeats).flatMap(List::stream).toList();
    }

    public Bill checkout(CustomerId customerId, Location location, TipRate groupTipRate) {
        Seat seat = getSeatByCustomerId(customerId);
        removeReservationIfLastMember(seat);
        return seat.checkout(location, groupTipRate);
    }

    private void removeReservationIfLastMember(Seat seat) {
        if (seat.isReservedForGroup()) {
            if (isLastFromGroup(seat.getGroupName().get())) {
                removeNotUsedReservationForTheGroup(seat.getGroupName().get());
            }
        }
    }

    private void removeNotUsedReservationForTheGroup(GroupName groupName) {
        getSeatsFromCubes().stream().filter(seat -> groupName.equals(seat.getGroupName().orElse(null)) && seat.isCurrentlyReserved())
            .forEach(Seat::removeReservation);
    }

    private boolean isLastFromGroup(GroupName groupName) {
        return getSeatsFromCubes().stream().filter(seat -> groupName.equals(seat.getGroupName().orElse(null))).filter(Seat::isCurrentlyOccupied).count() == 1;
    }

    public boolean isCustomerAlreadySeated(CustomerId customerId) {
        return getSeatsFromCubes().stream().filter(Seat::isCurrentlyOccupied).anyMatch(seat -> customerId.equals(seat.getCustomer().get().getId()));
    }

    public void placeOrder(CustomerId customerId, Order order, Inventory inventory) {
        Seat seat = getSeatByCustomerId(customerId);
        inventory.useIngredients(order.ingredientsNeeded());
        seat.placeOrder(order);
    }
}
