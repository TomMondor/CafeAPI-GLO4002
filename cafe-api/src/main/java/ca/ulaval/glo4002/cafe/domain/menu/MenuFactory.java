package ca.ulaval.glo4002.cafe.domain.menu;

import java.util.List;

public class MenuFactory {
    public Menu createMenu(List<Coffee> defaultMenuItems) {
        Menu menu = new Menu();
        insertDefaultMenuItems(menu, defaultMenuItems);
        return menu;
    }

    private void insertDefaultMenuItems(Menu menu, List<Coffee> defaultMenuItems) {
        for (Coffee item : defaultMenuItems) {
            menu.addMenuItem(item);
        }
    }
}
