package ruinMapper.hexagon.domain.hint;

import org.junit.Before;
import org.junit.Test;
import ruinMapper.fixtures.repository.*;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.model.ComponentFactory;
import ruinMapper.hexagon.domain.model.ContextSupplierPort;
import ruinMapper.hexagon.domain.room.RoomPort;

import static org.junit.Assert.*;

public class HintPortTest {
    private ContextPort context;
    private ContextSupplierPort lifecyclePort;

    private HintPort hintToTest;

    @Before
    public void setup() {
        lifecyclePort = implementationOne();
        context = lifecyclePort.createNewContext("");

        hintToTest = context
                .createArea("")
                .accessRoom(0, 0).createHint(
                        "The sacred points to the entry of the underworld");
    }

    // notes the type of Implementation of the test uses.
    // for testing your own implementation just write another method that returns
    // a a different ContextSupplierPort implementation and switch out the setup() method
    private ContextSupplierPort implementationOne() {
        return new ComponentFactory(
                new QuestRepoDummy(), new RoomRepoDummy(),
                new HintRepoDummy(), new TagRepoDummy(),
                new AreaRepoDummy(),
                new ContextRepoDummy());
    }

    @Test
    public void changeContent() {
        String nString = "Bow to the Beggar";
        hintToTest.changeContent(nString);

        assertEquals(nString, hintToTest.accessContent());
    }

    @Test
    public void accessContent() {
        assertNotNull(hintToTest.accessContent());
    }

    @Test
    public void changeNotes() {
        String nString = "Need doublejump";
        hintToTest.changeNotes(nString);

        assertEquals(nString, hintToTest.accessNotes());
    }

    @Test
    public void accessNotes() {
        assertNotNull(hintToTest.accessNotes());
    }

    @Test
    public void accessRoom() {
        assertNotNull(hintToTest.accessRoom());
    }

    @Test
    public void deleteHint() {
        RoomPort roomWithHint = context
                .createArea("A1")
                .accessRoom(0, 0);
        hintToTest = roomWithHint.createHint("asdf");

        hintToTest.deleteHint();

        assertFalse(roomWithHint.accessHints()
                .contains(hintToTest));
        assertFalse(
                context.accessEveryHint()
                        .contains(hintToTest));
    }

    @Test
    public void getHintStatus() {
        assertNotNull(hintToTest.getHintStatus());
    }

    @Test
    public void setHintStatus() {
        hintToTest.setHintStatus(HintStatus.HUNCH);
        assertEquals(HintStatus.HUNCH,
                hintToTest.getHintStatus());
    }
}