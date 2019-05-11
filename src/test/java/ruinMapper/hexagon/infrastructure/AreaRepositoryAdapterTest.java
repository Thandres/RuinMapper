package ruinMapper.hexagon.infrastructure;

import org.junit.Before;
import org.junit.Test;
import ruinMapper.fixtures.domain.AreaFixture;
import ruinMapper.fixtures.domain.LifecycleFixture;
import ruinMapper.fixtures.repository.ContextRepoDummy;
import ruinMapper.fixtures.repository.RoomRepoDummy;
import ruinMapper.hexagon.domain.area.Area;
import ruinMapper.hexagon.domain.model.ContextSupplierPort;
import ruinMapper.hexagon.infrastructure.dto.mapper.AreaMapper;

public class AreaRepositoryAdapterTest {
    private AreaRepositoryAdapter repositoryToTest;

    @Before
    public void setup() {
        ContextSupplierPort contextSupplierPort = LifecycleFixture
                .implementationOne();
        repositoryToTest = new AreaRepositoryAdapter(
                new AreaMapper("adsf",
                        new ContextRepoDummy(),
                        new RoomRepoDummy(),
                        repositoryToTest),
                "D:\\Repos\\");
    }

    @Test
    public void create() {
        Area area = AreaFixture.getFixture();
        repositoryToTest.create(area);
//        repositoryToTest.delete(area.toString());
    }

    @Test
    public void read() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}