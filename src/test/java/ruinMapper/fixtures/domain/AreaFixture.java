package ruinMapper.fixtures.domain;

import ruinMapper.fixtures.repository.AreaRepoDummy;
import ruinMapper.hexagon.domain.area.Area;

public class AreaFixture {
    public static Area getFixture() {
        return new Area("Meridia",

                ContextFixture.getContext(),
                new AreaRepoDummy());
    }
}
