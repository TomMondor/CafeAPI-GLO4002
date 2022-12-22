package ca.ulaval.glo4002.cafe.fixture.request;

import ca.ulaval.glo4002.cafe.api.inventory.request.InventoryRequest;

public class InventoryRequestFixture {
    public int chocolat = 100;
    public int espresso = 100;
    public int milk = 100;
    public int water = 100;

    public InventoryRequestFixture withChocolate(int chocolate) {
        this.chocolat = chocolate;
        return this;
    }

    public InventoryRequestFixture withEspresso(int espresso) {
        this.espresso = espresso;
        return this;
    }

    public InventoryRequestFixture withMilk(int milk) {
        this.milk = milk;
        return this;
    }

    public InventoryRequestFixture withWater(int water) {
        this.water = water;
        return this;
    }

    public InventoryRequest build() {
        InventoryRequest inventoryRequest = new InventoryRequest();
        inventoryRequest.Chocolate = chocolat;
        inventoryRequest.Espresso = espresso;
        inventoryRequest.Milk = milk;
        inventoryRequest.Water = water;
        return inventoryRequest;
    }
}
