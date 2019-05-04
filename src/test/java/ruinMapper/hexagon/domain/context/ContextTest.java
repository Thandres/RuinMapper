package ruinMapper.hexagon.domain.context;

import org.junit.Before;
import ruinMapper.hexagon.ComponentFactory;

public class ContextTest {

    private ContextPort context;

    @Before
    public void setup() {
        context = ComponentFactory.createContext();
    }



}