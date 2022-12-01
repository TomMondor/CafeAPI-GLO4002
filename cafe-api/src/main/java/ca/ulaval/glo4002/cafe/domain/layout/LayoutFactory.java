package ca.ulaval.glo4002.cafe.domain.layout;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeName;
import ca.ulaval.glo4002.cafe.domain.layout.cube.CubeSize;

public class LayoutFactory {
    public Layout createLayout(CubeSize cubeSize, List<CubeName> cubeNames) {
        return new Layout(cubeSize, cubeNames);
    }
}
