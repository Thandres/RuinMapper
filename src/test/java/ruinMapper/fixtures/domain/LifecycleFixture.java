package ruinMapper.fixtures.domain;

import ruinMapper.fixtures.repository.*;
import ruinMapper.hexagon.domain.model.ComponentFactory;
import ruinMapper.hexagon.domain.model.ContextSupplierPort;

public class LifecycleFixture {
    public static ContextSupplierPort implementationOne() {
        return new ComponentFactory(
                new QuestRepoDummy(), new RoomRepoDummy(),
                new HintRepoDummy(), new TagRepoDummy(),
                new AreaRepoDummy(), new ContextRepoDummy(),
                new StateKeeperRepoDummy());
    }
}
