package ca.ulaval.glo4002.cafe.fixture;

import ca.ulaval.glo4002.cafe.domain.menu.Menu;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;

public class MenuFixture {
    private final Menu menu = new Menu();

    public MenuFixture withItem(MenuItem item) {
        menu.addMenuItem(item);
        return this;
    }

    public Menu build() {
        return menu;
    }
}
