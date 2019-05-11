package ruinMapper.fixtures.domain;

import ruinMapper.fixtures.repository.ContextRepoDummy;
import ruinMapper.hexagon.domain.context.Context;

public class ContextFixture {
    public static Context getContext() {
        return new Context("Fixture",
                new ContextRepoDummy());
    }
}
