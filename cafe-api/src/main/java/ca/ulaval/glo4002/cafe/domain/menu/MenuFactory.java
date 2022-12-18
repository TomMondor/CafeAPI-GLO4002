package ca.ulaval.glo4002.cafe.domain.menu;

import java.util.List;

public class MenuFactory {
    public Menu createMenu(List<MenuItem> defaultMenuItems) {
        Menu menu = new Menu();
        insertDefaultMenuItems(menu, defaultMenuItems);
        return menu;
    }

    private void insertDefaultMenuItems(Menu menu, List<MenuItem> defaultMenuItems) {
        for (MenuItem item : defaultMenuItems) {
            menu.addMenuItem(item);
        }
    }
}
