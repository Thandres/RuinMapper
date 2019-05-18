package ruinMapper.hexagon.domain.model;

import org.junit.Before;
import org.junit.Test;
import ruinMapper.fixtures.repository.*;
import ruinMapper.hexagon.domain.ContextSupplierPort;
import ruinMapper.hexagon.domain.DomainInjector;

public class ContextSupplierPortTest {

    private ContextSupplierPort supplierToTest;


    @Before
    public void setup() {
//        supplierToTest = implementationOne();

    }

    // notes the type of Implementation of the test uses.
    // for testing your own implementation just write another method that returns
    // a a different ContextSupplierPort implementation and switch out the setup() method
    private ContextSupplierPort implementationOne() {
        return new DomainInjector(
                new QuestRepoDummy(), new RoomRepoDummy(),
                new HintRepoDummy(), new TagRepoDummy(),
                new AreaRepoDummy(),
                new ContextRepoDummy());
    }

    @Test
    public void createNewContext() {
        //TODO Integrationtest with working Repository?
//        ContextPort contextPort = supplierToTest
//                .createNewContext("Test");

        // assertNotNull(
        //       supplierToTest.loadContextByName("Test"));
    }

    @Test
    public void loadContextByName() {
        // same test as create Context(maybe)?
    }
}