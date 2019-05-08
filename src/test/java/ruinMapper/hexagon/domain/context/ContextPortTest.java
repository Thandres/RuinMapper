package ruinMapper.hexagon.domain.context;

import org.junit.Before;
import org.junit.Test;
import ruinMapper.fixtures.*;
import ruinMapper.hexagon.domain.area.AreaPort;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.model.ComponentFactory;
import ruinMapper.hexagon.domain.model.ContextSupplierPort;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.room.RoomPort;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.util.Set;

import static org.junit.Assert.*;

public class ContextPortTest {
    private ContextPort contextToTest;

    private static final String CONTEXT_NAME = "Metroid";


    @Before
    public void setup() {
        ContextSupplierPort lifecyclePort = implementationOne();
        contextToTest = lifecyclePort
                .createNewContext(CONTEXT_NAME);

    }

    // notes the type of Implementation of the test uses.
    // for testing your own implementation just write another method that returns
    // a a different ContextSupplierPort implementation and switch out the setup() method
    private ContextSupplierPort implementationOne() {
        return new ComponentFactory(
                new QuestRepoDummy(), new RoomRepoDummy(),
                new HintRepoDummy(), new TagRepoDummy(),
                new AreaRepoDummy(), new ContextRepoDummy(),
                new StateKeeperRepoDummy());
    }

    @Test
    public void accessName() {
        assertEquals(CONTEXT_NAME,
                contextToTest.accessName());
    }

    @Test
    public void createArea() {
        String nStr = "Temple Steppes";
        AreaPort areaPort = contextToTest.createArea(nStr);
        assertEquals(areaPort,
                contextToTest.accessArea(nStr));
        assertTrue(contextToTest.accessEveryArea()
                .contains(areaPort));

    }

    @Test
    public void accessArea() {
        String nStr = "Temple Steppes";
        AreaPort areaPort = contextToTest.createArea(nStr);
        assertNotNull(contextToTest.accessArea(nStr));
        assertEquals(areaPort,
                contextToTest.accessArea(nStr));

    }

    @Test
    public void accessEveryArea() {

        AreaPort areaPort1 = contextToTest.createArea("A1");
        AreaPort areaPort2 = contextToTest.createArea("A2");
        AreaPort areaPort3 = contextToTest.createArea("A3");

        Set<AreaPort> areaPorts = contextToTest
                .accessEveryArea();
        assertNotNull(areaPorts);
        assertTrue(areaPorts.contains(areaPort1));
        assertTrue(areaPorts.contains(areaPort2));
        assertTrue(areaPorts.contains(areaPort3));
    }

    @Test
    public void createValidTag() {
        TagPort tag = contextToTest
                .createValidTag("newTag");
        assertNotNull(tag);
        assertTrue(contextToTest.accessValidTags()
                .contains(tag));
    }

    @Test
    public void accessValidTags() {
        assertNotNull(contextToTest.accessValidTags());
    }

    @Test
    public void createQuest() {
        QuestPort createdQuest = contextToTest
                .createQuest("newQuest");
        assertNotNull(createdQuest);
        assertTrue(contextToTest.accessEveryQuest()
                .contains(createdQuest));
    }

    @Test
    public void deleteQuest() {
        QuestPort quest = contextToTest.createQuest("Q1");
        RoomPort room = contextToTest.createArea("A1")
                .accessRoom(0, 0);
        quest.addQuestRoom(room);

        quest.deleteQuest();

        assertFalse(room.accessQuests().contains(quest));
        assertFalse(contextToTest.accessEveryQuest()
                .contains(quest));
    }

    @Test
    public void accessEveryQuest() {
        QuestPort q1 = contextToTest.createQuest("Q1");
        RoomPort room = contextToTest.createArea("A1")
                .accessRoom(0, 0);
        QuestPort q2 = room.createQuest("Q2");

        Set<QuestPort> setOfQuests = contextToTest
                .accessEveryQuest();

        assertNotNull(setOfQuests);
        assertTrue(setOfQuests.contains(q1));
        assertTrue(setOfQuests.contains(q2));

    }

    @Test
    public void accessEveryHint() {
        QuestPort q1 = contextToTest.createQuest("Q1");
        RoomPort room = contextToTest.createArea("A1")
                .accessRoom(0, 0);
        HintPort h1 = room.createHint("H1");
        HintPort h2 = room.createHint("H2");

        RoomPort r2 = contextToTest.createArea("A2")
                .accessRoom(0, 0);

        HintPort h3 = r2.createHint("H3");
        HintPort h4 = r2.createHint("H4");

        Set<HintPort> setOfHints = contextToTest
                .accessEveryHint();
        assertNotNull(setOfHints);
        assertTrue(setOfHints.contains(h1));
        assertTrue(setOfHints.contains(h2));
        assertTrue(setOfHints.contains(h3));
        assertTrue(setOfHints.contains(h4));

    }

    @Test
    public void deleteContext() {
        contextToTest.createQuest("Q1");
        contextToTest.createArea("A1");
        contextToTest.createValidTag("");

        contextToTest.deleteContext();

        assertTrue(
                contextToTest.accessEveryArea().isEmpty());
        assertTrue(
                contextToTest.accessEveryQuest().isEmpty());
        assertTrue(contextToTest.accessValidTags()
                .isEmpty());
    }
}