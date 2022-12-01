package ca.ulaval.glo4002.cafe.api.layout.response;

import ca.ulaval.glo4002.cafe.api.layout.SeatStatus;

public record SeatResponse(int number, SeatStatus status, String customer_id, String group_name) {
}
