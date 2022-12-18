package ca.ulaval.glo4002.cafe.domain.menu;

import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4002.cafe.domain.exception.DuplicateMenuItemNameException;
import ca.ulaval.glo4002.cafe.domain.exception.InvalidMenuOrderException;

public class Menu {
    private final Map<MenuItemName, MenuItem> menuItems = new HashMap<>();

    public void addMenuItem(MenuItem menuItem) {
        if (menuItems.containsKey(menuItem.name())) {
            throw new DuplicateMenuItemNameException();
        }
        menuItems.put(menuItem.name(), menuItem);
    }

    public MenuItem findMenuItemByName(MenuItemName menuItemName) {
        if (!menuItems.containsKey(menuItemName)) {
            throw new InvalidMenuOrderException();
        }
        return menuItems.get(menuItemName);
    }
}
