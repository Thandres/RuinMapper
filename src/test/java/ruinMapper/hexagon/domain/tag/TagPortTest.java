package ruinMapper.hexagon.domain.tag;

import org.junit.Before;
import org.junit.Test;
import ruinMapper.fixtures.*;
import ruinMapper.hexagon.domain.area.AreaPort;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.model.ComponentFactory;
import ruinMapper.hexagon.domain.model.DomainLifecyclePort;
import ruinMapper.hexagon.domain.room.RoomPort;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TagPortTest {

    private ContextPort context;
    private DomainLifecyclePort lifecyclePort;

    private TagPort tagToTest;

    @Before
    public void setup() {
        lifecyclePort = implementationOne();
        context = lifecyclePort.createNewContext("");
        tagToTest = context.createTag("Shop");
    }

    // notes the type of Implementation of the test uses.
    // for testing your own implementation just write another method that returns
    // a a different DomainLifecyclePort implementation and switch out the setup() method
    private DomainLifecyclePort implementationOne() {
        return new ComponentFactory(
                new QuestRepoDummy(), new RoomRepoDummy(),
                new HintRepoDummy(), new TagRepoDummy(),
                new AreaRepoDummy(), new ContextRepoDummy(),
                new StateKeeperRepoDummy());
    }

    @Test
    public void accessTag() {
        // Basically getter, needs no testing
    }

    @Test
    public void changeTag() {
        String nString = "Dungeon";
        tagToTest.changeTag(nString);

        assertEquals(nString, tagToTest.accessTag());
    }

    @Test
    public void deleteTag() {
        AreaPort area = context.createArea("");
        RoomPort r1 = area.createRoom(1, 2);
        r1.addTag(tagToTest);
        RoomPort r2 = area.createRoom(2, 2);
        r1.addTag(tagToTest);

        tagToTest.deleteTag();

        assertFalse(context.accessPossibleTags()
                .contains(tagToTest));
        assertFalse(r1.accessTags().contains(tagToTest));
        assertFalse(r2.accessTags().contains(tagToTest));

    }
}