package ruinMapper.hexagon.domain.quest;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import ruinMapper.hexagon.domain.ContextSupplier;
import ruinMapper.hexagon.domain.ContextSupplierPort;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.room.RoomPort;

import java.io.File;
import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.*;

public class QuestPortTest {
    private ContextPort context;

    private QuestPort questToTest;
    private static final String CONTEXT_NAME = "map";
    private static final String TEST_DIRECTORY = "D:\\Repos\\map";

    @Before
    public void setup() {
        File dir = new File(TEST_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        ContextSupplierPort lifecyclePort = new ContextSupplier(
                TEST_DIRECTORY);
        context = lifecyclePort
                .createNewContext(CONTEXT_NAME);
        questToTest = context
                .createQuest("");
    }

    @AfterClass
    public static void deleteTestFiles() {
        File dir = new File(TEST_DIRECTORY);
        if (dir.exists()) {
            Arrays.stream(dir.listFiles())
                    .forEach(file -> file.delete());
            dir.delete();
        }
    }
    @Test
    public void changeTitle() {
        String nStr = "Save the Kingdom";

        questToTest.changeTitle(nStr);

        assertEquals(nStr, questToTest.accessTitle());
    }

    @Test
    public void accessTitle() {
        assertNotNull(questToTest.accessTitle());
    }

    @Test
    public void changeDescription() {
        String nStr = "Defeat the rebel leader to save the kingdom";

        questToTest.changeDescription(nStr);

        assertEquals(nStr, questToTest.accessDescription());
    }

    @Test
    public void accessDescription() {
        assertNotNull(questToTest.accessDescription());
    }

    @Test
    public void changeNotes() {
        String nStr = "Rebel leader weak to fire";

        questToTest.changeNotes(nStr);

        assertEquals(nStr, questToTest.accessNotes());
    }

    @Test
    public void accessNotes() {
        assertNotNull(questToTest.accessNotes());
    }

    @Test
    public void accessQuestStatus() {
        assertNotNull(questToTest.accessQuestStatus());
    }

    @Test
    public void changeQuestStatus() {
        questToTest.changeQuestStatus(QuestStatus.SOLVED);
        assertEquals(QuestStatus.SOLVED,
                questToTest.accessQuestStatus());
    }

    @Test
    public void accessQuestRooms() {
        RoomPort r1 = context.createArea("A1")
                .accessRoom(0, 0);

        RoomPort r2 = context.createArea("A2")
                .accessRoom(0, 0);
        r1.addQuest(questToTest);
        questToTest.addQuestRoom(r2);

        Set<RoomPort> setOfRooms = questToTest
                .accessQuestRooms();

        assertTrue(setOfRooms.contains(r1));
        assertTrue(setOfRooms.contains(r2));
    }

    @Test
    public void addQuestRoom() {
        RoomPort r1 = context.createArea("A1")
                .accessRoom(0, 0);

        questToTest.addQuestRoom(r1);

        assertTrue(questToTest.accessQuestRooms()
                .contains(r1));
        assertTrue(r1.accessQuests().contains(questToTest));

    }

    @Test
    public void removeQuestRoom() {
        RoomPort r1 = context.createArea("A1")
                .accessRoom(0, 0);
        questToTest.addQuestRoom(r1);

        questToTest.removeQuestRoom(r1);

        assertFalse(questToTest.accessQuestRooms()
                .contains(r1));
        assertFalse(
                r1.accessQuests().contains(questToTest));
    }

    @Test
    public void deleteQuest() {
        RoomPort r1 = context.createArea("A1")
                .accessRoom(0, 0);
        RoomPort r2 = context.createArea("A2")
                .accessRoom(0, 0);
        r1.addQuest(questToTest);
        questToTest.addQuestRoom(r2);

        questToTest.deleteQuest();

        assertFalse(context.accessEveryQuest()
                .contains(questToTest));
        assertFalse(
                r1.accessQuests().contains(questToTest));
        assertFalse(
                r2.accessQuests().contains(questToTest));

    }
}