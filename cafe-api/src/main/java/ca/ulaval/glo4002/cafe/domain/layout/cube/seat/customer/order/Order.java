package ca.ulaval.glo4002.cafe.domain.layout.cube.seat.customer.order;

import java.util.List;
import java.util.stream.Stream;

import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;

public record Order(List<Coffee> items) {
    public Order addAll(Order otherOrder) {
        return new Order(Stream.concat(items().stream(), otherOrder.items().stream()).toList());
    }

    public List<Ingredient> ingredientsNeeded() {
        return items.stream().map(coffee -> coffee.recipe().ingredients()).flatMap(List::stream).toList();
    }
}
