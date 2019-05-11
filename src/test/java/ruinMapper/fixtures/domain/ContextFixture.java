package ruinMapper.fixtures.domain;

import ruinMapper.fixtures.repository.ContextRepoDummy;
import ruinMapper.hexagon.domain.context.Context;

import java.util.UUID;

public class ContextFixture {
    public static Context getContext() {
        return new Context("Fixture",
                new ContextRepoDummy(), UUID
                .randomUUID());
    }
}
