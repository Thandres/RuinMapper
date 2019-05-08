package ruinMapper.fixtures.domain;

import ruinMapper.fixtures.repository.AreaRepoDummy;
import ruinMapper.hexagon.domain.area.Area;

import java.util.HashMap;
import java.util.UUID;

public class AreaFixture {
    public static Area getFixture() {
        return new Area("Meridia", "Not friendly",
                new HashMap<>(),
                null, new AreaRepoDummy(),
                UUID
                        .randomUUID());
    }
}
