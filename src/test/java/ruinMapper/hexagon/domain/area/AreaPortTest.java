package ruinMapper.hexagon.domain.area;

import org.junit.Before;
import org.junit.Test;
import ruinMapper.fixtures.*;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.model.ComponentFactory;
import ruinMapper.hexagon.domain.model.DomainLifecyclePort;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.room.RoomPort;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class AreaPortTest {
    private ContextPort context;
    private DomainLifecyclePort lifecyclePort;

    private AreaPort areaToTest;

    @Before
    public void setup() {
        lifecyclePort = implementationOne();
        context = lifecyclePort.createNewContext("");
        areaToTest = context
                .accessArea(ComponentFactory.NEW_AREA_NAME);
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
    public void createRoom() {
        RoomPort createdRoom = areaToTest.createRoom(1, 2);
        assertNotNull(createdRoom);
        assertTrue(areaToTest.accessRooms()
                .contains(createdRoom));
    }

    @Test
    public void accessRoom() {
        RoomPort createdRoom = areaToTest.createRoom(1, 2);
        RoomPort existingRoom = areaToTest.accessRoom(1, 2);
        assertNotNull(existingRoom);
        assertEquals(createdRoom, existingRoom);
    }

    @Test
    public void accessRooms() {
        // basically getter, needs no testing
    }

    @Test
    public void deleteRoom() {
        RoomPort roomToDelete = areaToTest.createRoom(1, 2);
        QuestPort assignedQuest = roomToDelete
                .createQuest("");
        HintPort assignedHint = roomToDelete.createHint("");

        areaToTest.deleteRoom(1, 2);

        assertFalse(assignedQuest.accessQuestRooms()
                .contains(roomToDelete));
        assertNull(assignedHint.accessRoom());
    }

    @Test
    public void changeTitle() {
        String nTitle = "Sunken Valley";
        areaToTest.changeTitle(nTitle);

        assertEquals(nTitle, areaToTest.accessTitle());
    }

    @Test
    public void accessTitle() {
        // basically getter, needs no testing
    }

    @Test
    public void changeNotes() {
        String nNotes = "Boss has item for last area?";
        areaToTest.changeNotes(nNotes);

        assertEquals(nNotes, areaToTest.accessNotes());
    }

    @Test
    public void accessNotes() {
        // basically getter, needs no testing
    }

    @Test
    public void accessHintsOnArea() {
        RoomPort roomWithHints1 = areaToTest
                .createRoom(1, 2);
        Set<HintPort> check = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            check.add(roomWithHints1.createHint("one" + i));
        }
        RoomPort roomWithHints2 = areaToTest
                .createRoom(1, 3);
        for (int i = 0; i < 4; i++) {
            check.add(roomWithHints2.createHint("two" + i));
        }

        Set<HintPort> hintSet = areaToTest
                .accessHintsOnArea();

        assertTrue(hintSet.containsAll(check));
    }

    @Test
    public void deleteArea() {
        RoomPort room1 = areaToTest.createRoom(1, 2);
        HintPort hint1 = room1.createHint("");
        RoomPort room2 = areaToTest.createRoom(2, 2);
        HintPort hint2 = room2.createHint("");

        areaToTest.deleteArea();

        assertFalse(
                context.accessAreas().contains(areaToTest));
        assertFalse(context.accessHints().contains(hint1));
        assertFalse(context.accessHints().contains(hint2));

    }
}