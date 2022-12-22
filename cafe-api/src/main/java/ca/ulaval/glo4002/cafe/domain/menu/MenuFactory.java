package ca.ulaval.glo4002.cafe.domain.menu;

import java.util.List;

public class MenuFactory {
    public Menu createMenu(List<Coffee> defaultMenuItems) {
        return new Menu(defaultMenuItems);
    }
}
