package ruinMapper.hexagon.infrastructure;

import org.junit.Before;
import org.junit.Test;
import ruinMapper.fixtures.domain.LifecycleFixture;
import ruinMapper.fixtures.repository.ContextRepoDummy;
import ruinMapper.fixtures.repository.RoomRepoDummy;
import ruinMapper.hexagon.domain.model.ContextSupplierPort;
import ruinMapper.hexagon.infrastructure.persistence.area.AreaMapper;
import ruinMapper.hexagon.infrastructure.persistence.area.AreaRepository;

public class AreaRepositoryTest {
    private AreaRepository repositoryToTest;

    @Before
    public void setup() {
        ContextSupplierPort contextSupplierPort = LifecycleFixture
                .implementationOne();
        repositoryToTest = new AreaRepository(
                new AreaMapper("adsf",
                        new ContextRepoDummy(),
                        new RoomRepoDummy(),
                        repositoryToTest),
                "D:\\Repos\\");
    }

    @Test
    public void create() {
//        Area area = AreaFixture.getFixture();
//        repositoryToTest.create(area);
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