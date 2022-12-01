package ca.ulaval.glo4002.cafe.api.layout.response;

import java.util.List;

public record CubeResponse(String name, List<SeatResponse> seats) {
}
