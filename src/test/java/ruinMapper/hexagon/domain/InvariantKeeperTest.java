package ruinMapper.hexagon.domain;

import org.junit.Before;
import org.junit.Test;
import ruinMapper.fixtures.*;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.invariant.InvariantKeeper;
import ruinMapper.hexagon.domain.model.ComponentFactory;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.room.RoomPort;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InvariantKeeperTest {

    private InvariantKeeper stateKeeper;

    @Before
    public void setup() {
        ComponentFactory
                .setAreaRepository(new AreaRepoDummy());
        ComponentFactory.setContextRepository(
                new ContextRepoDummy());
        ComponentFactory
                .setQuestRepository(new QuestRepoDummy());
        ComponentFactory
                .setTagRepository(new TagRepoDummy());
        ComponentFactory
                .setHintRepository(new HintRepoDummy());
        ComponentFactory
                .setRoomRepository(new RoomRepoDummy());
        ComponentFactory.setStateKeeperRepository(
                new StateKeeperRepoDummy());
        stateKeeper = ComponentFactory.createStateKeeper();
        ComponentFactory.setStateKeeper(stateKeeper);

    }

    @Test
    public void QuestRoomInvarianttest() {
        RoomPort room = ComponentFactory.createRoom();
        ContextPort context = ComponentFactory
                .createContext();
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

        quest.addRoom(room);
        assertTrue(room.accessQuests().contains(quest));
        assertTrue(quest.accessQuestRooms().contains(room));

        quest.removeRoom(room);
        assertFalse(room.accessQuests().contains(quest));
        assertFalse(
                quest.accessQuestRooms().contains(room));

        quest.addRoom(room);
        quest.deleteQuest();
        assertFalse(room.accessQuests().contains(quest));
        assertFalse(context.accessQuests().contains(quest));

        quest = context.createQuest("");
        quest.addRoom(room);
        context.deleteQuest(quest);
        assertFalse(room.accessQuests().contains(quest));
        assertFalse(context.accessQuests().contains(quest));
    }

}