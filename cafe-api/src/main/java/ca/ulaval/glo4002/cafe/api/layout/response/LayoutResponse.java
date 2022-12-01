package ca.ulaval.glo4002.cafe.api.layout.response;

import java.util.List;

public record LayoutResponse(String name, List<CubeResponse> cubes) {
}
