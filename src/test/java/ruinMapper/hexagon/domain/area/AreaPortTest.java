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

import static org.junit.Assert.*;

public class AreaPortTest {
    private ContextPort context;
    private DomainLifecyclePort lifecyclePort;

    private AreaPort areaToTest;

    @Before
    public void setup() {
        lifecyclePort = new ComponentFactory(
                new QuestRepoDummy(), new RoomRepoDummy(),
                new HintRepoDummy(), new TagRepoDummy(),
                new AreaRepoDummy(), new ContextRepoDummy(),
                new StateKeeperRepoDummy());
        context = lifecyclePort.createNewContext("");
        areaToTest = context
                .accessArea(ComponentFactory.NEW_AREA_NAME);
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
    }

    @Test
    public void accessTitle() {
    }

    @Test
    public void changeNotes() {
    }

    @Test
    public void accessNotes() {
    }

    @Test
    public void accessHintsOnArea() {
    }

    @Test
    public void deleteArea() {
    }
}