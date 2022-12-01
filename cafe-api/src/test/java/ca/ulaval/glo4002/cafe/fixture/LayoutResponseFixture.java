package ca.ulaval.glo4002.cafe.fixture;

import java.util.List;

import ca.ulaval.glo4002.cafe.api.layout.response.CubeResponse;
import ca.ulaval.glo4002.cafe.api.layout.response.LayoutResponse;

public class LayoutResponseFixture {
    private String name = "Les 4-fées";
    private List<CubeResponse> cubes = List.of(new CubeResponseFixture().build());

    public LayoutResponseFixture withName(String name) {
        this.name = name;
        return this;
    }

    public LayoutResponseFixture withCubeResponses(List<CubeResponse> cubes) {
        this.cubes = cubes;
        return this;
    }

    public LayoutResponse build() {
        return new LayoutResponse(name, cubes);
    }
}
