package ruinMapper.hexagon.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import ruinMapper.fixtures.domain.AreaFixture;
import ruinMapper.fixtures.domain.LifecycleFixture;
import ruinMapper.hexagon.domain.model.ContextSupplierPort;
import ruinMapper.hexagon.infrastructure.dto.mapper.AreaMapper;

public class AreaRepositoryTest {
    private AreaRepository repositoryToTest;

    @Before
    public void setup() {
        ContextSupplierPort contextSupplierPort = LifecycleFixture
                .implementationOne();
        repositoryToTest = new AreaRepository(
                new ObjectMapper(),
                new AreaMapper(), "D:\\Repos\\save.json");
    }

    @Test
    public void create() {
        repositoryToTest.create(AreaFixture.getFixture());
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