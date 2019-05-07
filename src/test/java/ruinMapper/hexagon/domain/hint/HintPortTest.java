package ruinMapper.hexagon.domain.hint;

import org.junit.Before;
import org.junit.Test;
import ruinMapper.fixtures.*;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.model.ComponentFactory;
import ruinMapper.hexagon.domain.model.DomainLifecyclePort;
import ruinMapper.hexagon.domain.room.RoomPort;

import static org.junit.Assert.*;

public class HintPortTest {
    private ContextPort context;
    private DomainLifecyclePort lifecyclePort;

    private HintPort hintToTest;

    @Before
    public void setup() {
        lifecyclePort = implementationOne();
        context = lifecyclePort.createNewContext("");

        hintToTest = context
                .accessArea(ComponentFactory.NEW_AREA_NAME)
                .accessRoom(0, 0).createHint(
                        "The sacred points to the entry of the underworld");
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
    public void changeContent() {
        String nString = "Bow to the Beggar";
        hintToTest.changeContent(nString);

        assertEquals(nString, hintToTest.accessContent());
    }

    @Test
    public void accessContent() {
        // Basically getter, needs no testing
    }

    @Test
    public void changeNotes() {
        String nString = "Need doublejump";
        hintToTest.changeNotes(nString);

        assertEquals(nString, hintToTest.accessNotes());
    }

    @Test
    public void accessNotes() {
        // Basically getter, needs no testing
    }

    @Test
    public void accessRoom() {
        assertNotNull(hintToTest.accessRoom());
    }

    @Test
    public void deleteHint() {
        RoomPort roomWithHint = context
                .accessArea(ComponentFactory.NEW_AREA_NAME)
                .accessRoom(0, 0);
        assertTrue(roomWithHint.accessHints()
                .contains(hintToTest));
        hintToTest.deleteHint();
        assertFalse(roomWithHint.accessHints()
                .contains(hintToTest));
        assertFalse(
                context.accessHints().contains(hintToTest));
    }

    @Test
    public void getHintStatus() {
        // Basically getter, needs no testing
    }

    @Test
    public void setHintStatus() {
        hintToTest.setHintStatus(HintStatus.HUNCH);
        assertEquals(HintStatus.HUNCH,
                hintToTest.getHintStatus());
    }
}