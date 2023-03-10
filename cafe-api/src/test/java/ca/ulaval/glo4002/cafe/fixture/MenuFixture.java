package ca.ulaval.glo4002.cafe.fixture;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.menu.Coffee;
import ca.ulaval.glo4002.cafe.domain.menu.Menu;

public class MenuFixture {
    private final Menu menu = new Menu(List.of());

    public MenuFixture withItem(Coffee item) {
        menu.addMenuItem(item);
        return this;
    }

    public Menu build() {
        return menu;
    }
}
