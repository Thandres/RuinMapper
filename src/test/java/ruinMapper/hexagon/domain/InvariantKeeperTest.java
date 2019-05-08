package ruinMapper.hexagon.domain;

import org.junit.Before;
import org.junit.Test;
import ruinMapper.fixtures.*;
import ruinMapper.hexagon.domain.area.AreaPort;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.model.ComponentFactory;
import ruinMapper.hexagon.domain.model.ContextSupplierPort;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.room.RoomPort;

import static org.junit.Assert.*;

public class InvariantKeeperTest {

    private ContextPort context;
    private ContextSupplierPort lifecyclePort;
    private static final String NEW_AREA_NAME = "New Area";

    @Before
    public void setup() {
        lifecyclePort = new ComponentFactory(
                new QuestRepoDummy(), new RoomRepoDummy(),
                new HintRepoDummy(), new TagRepoDummy(),
                new AreaRepoDummy(), new ContextRepoDummy(),
                new StateKeeperRepoDummy());
        context = lifecyclePort.createNewContext("");
    }

    @Test
    public void DomainLifecycleInvariantTest() {
        // createNewContext
        context = lifecyclePort.createNewContext("");
        AreaPort area = context.accessArea(NEW_AREA_NAME);
        assertNotNull(area);
        assertNotNull(area.accessRoom(0, 0));

        // deleteContext
        //TODO

        // loadContextByID
        // TODO

    }

    @Test
    //TODO split test into different tests and check for right asserts
    public void QuestRoomInvarianttest() {
        RoomPort room = context.accessArea(NEW_AREA_NAME)
                .accessRoom(0, 0);
        QuestPort quest = context.createQuest("");

        room.addQuest(quest);
        assertTrue(room.accessQuests().contains(quest));
        assertTrue(quest.accessQuestRooms().contains(room));

        room.removeQuest(quest);
        assertFalse(room.accessQuests().contains(quest));
        assertFalse(
                quest.accessQuestRooms().contains(room));

        QuestPort newQuest = room.createQuest("");
        assertTrue(room.accessQuests().contains(newQuest));
        assertTrue(
                newQuest.accessQuestRooms().contains(room));

        quest.addQuestRoom(room);
        assertTrue(room.accessQuests().contains(quest));
        assertTrue(quest.accessQuestRooms().contains(room));

        quest.removeQuestRoom(room);
        assertFalse(room.accessQuests().contains(quest));
        assertFalse(
                quest.accessQuestRooms().contains(room));

        quest.addQuestRoom(room);
        quest.deleteQuest();
        assertFalse(room.accessQuests().contains(quest));
        assertFalse(
                context.accessEveryQuest().contains(quest));

        quest = context.createQuest("");
        quest.addQuestRoom(room);
        context.deleteQuest(quest);
        assertFalse(room.accessQuests().contains(quest));
        assertFalse(
                context.accessEveryQuest().contains(quest));
    }

}