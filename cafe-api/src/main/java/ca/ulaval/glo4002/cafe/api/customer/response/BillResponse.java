package ca.ulaval.glo4002.cafe.api.customer.response;

import java.util.List;

public record BillResponse(List<String> orders, float subtotal, float taxes, float tip, float total) {
}
