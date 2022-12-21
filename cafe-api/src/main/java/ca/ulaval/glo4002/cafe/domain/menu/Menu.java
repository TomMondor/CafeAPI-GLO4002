package ca.ulaval.glo4002.cafe.domain.menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ulaval.glo4002.cafe.domain.exception.DuplicateMenuItemNameException;
import ca.ulaval.glo4002.cafe.domain.exception.InvalidMenuOrderException;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.Order;
import ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order.PendingOrder;

public class Menu {
    private final Map<CoffeeName, Coffee> menuItems = new HashMap<>();

    public void addMenuItem(Coffee menuItem) {
        if (menuItems.containsKey(menuItem.name())) {
            throw new DuplicateMenuItemNameException();
        }
        menuItems.put(menuItem.name(), menuItem);
    }

    public Coffee findMenuItemByName(CoffeeName menuItemName) {
        if (!menuItems.containsKey(menuItemName)) {
            throw new InvalidMenuOrderException();
        }
        return menuItems.get(menuItemName);
    }

    public Order approveOrder(PendingOrder pendingOrder) {
        List<Coffee> approvedItems = pendingOrder.items().stream().map(this::findMenuItemByName).toList();
        return new Order(approvedItems);
    }
}
