package ruinMapper.hexagon.domain.room;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import ruinMapper.fixtures.repository.*;
import ruinMapper.hexagon.application.DomainAdapter;
import ruinMapper.hexagon.domain.ContextSupplierPort;
import ruinMapper.hexagon.domain.DomainInjector;
import ruinMapper.hexagon.domain.area.AreaPort;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.awt.*;
import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.*;

public class RoomPortTest {
    private ContextPort context;

    private RoomPort roomToTest;
    private static final String CONTEXT_NAME = "map";
    private static final String TEST_DIRECTORY = "D:\\Repos\\map";


    @Before
    public void setup() {
        File dir = new File(TEST_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        DomainAdapter lifecyclePort = new DomainAdapter(
                TEST_DIRECTORY);
        context = lifecyclePort
                .createNewContext(CONTEXT_NAME);
        roomToTest = context
                .createArea("A1").accessRoom(0, 0);
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
    // notes the type of Implementation of the test uses.
    // for testing your own implementation just write another method that returns
    // a a different ContextSupplierPort implementation and switch out the setup() method
    private ContextSupplierPort implementationOne() {
        return new DomainInjector(
                new QuestRepoDummy(), new RoomRepoDummy(),
                new HintRepoDummy(), new TagRepoDummy(),
                new AreaRepoDummy(),
                new ContextRepoDummy());
    }

    @Test
    public void changeTitle() {
        String nStr = "The Promenade";
        roomToTest.changeTitle(nStr);

        assertEquals(nStr, roomToTest.accessTitle());
    }

    @Test
    public void accessTitle() {
        assertNotNull(roomToTest.accessTitle());
    }

    @Test
    public void changeNotes() {
        String nStr = "The Promenade";
        roomToTest.changeNotes(nStr);

        assertEquals(nStr, roomToTest.accessNotes());
    }

    @Test
    public void accessNotes() {
        assertNotNull(roomToTest.accessNotes());
    }

    @Test
    public void createHint() {
        HintPort createdHint = roomToTest.createHint("H1");

        assertTrue(roomToTest.accessHints()
                .contains(createdHint));
        assertEquals(roomToTest, createdHint.accessRoom());
    }

    @Test
    public void deleteHint() {
        HintPort hintToDelete = roomToTest.createHint("H1");

        roomToTest.deleteHint(hintToDelete);

        assertFalse(roomToTest.accessHints()
                .contains(hintToDelete));
        assertNull(hintToDelete.accessRoom());
        assertFalse(context.accessEveryHint()
                .contains(hintToDelete));
    }

    @Test
    public void accessHints() {
        assertNotNull(roomToTest.accessHints());
    }

    @Test
    public void createQuest() {
        QuestPort createdQuest = roomToTest
                .createQuest("Q1");

        assertTrue(roomToTest.accessQuests()
                .contains(createdQuest));
        assertTrue(createdQuest.accessQuestRooms()
                .contains(roomToTest));
        assertTrue(context.accessEveryQuest()
                .contains(createdQuest));
    }

    @Test
    public void addQuest() {
        QuestPort questToAdd = context.createQuest("Q1");

        roomToTest.addQuest(questToAdd);

        assertTrue(roomToTest.accessQuests()
                .contains(questToAdd));
        assertTrue(questToAdd.accessQuestRooms()
                .contains(roomToTest));
    }

    @Test
    public void removeQuest() {
        QuestPort questToRemove = roomToTest
                .createQuest("Q1");

        roomToTest.removeQuest(questToRemove);

        assertFalse(roomToTest.accessQuests()
                .contains(questToRemove));
        assertFalse(questToRemove.accessQuestRooms()
                .contains(roomToTest));
    }

    @Test
    public void accessQuests() {
        assertNotNull(roomToTest.accessQuests());
    }

    @Test
    public void tagRoom() {
        TagPort validTag = context.createValidTag("Shop");
        TagPort invalidTag = context.createValidTag("bla");
        invalidTag.deleteTag();

        roomToTest.tagRoom(validTag);
        roomToTest.tagRoom(invalidTag);

        assertTrue(
                roomToTest.accessTags().contains(validTag));
        assertFalse(roomToTest.accessTags()
                .contains(invalidTag));
    }

    @Test
    public void removeTag() {
        TagPort tagToRemove = context
                .createValidTag("Shop");
        roomToTest.tagRoom(tagToRemove);

        roomToTest.removeTag(tagToRemove);

        assertFalse(roomToTest.accessTags()
                .contains(tagToRemove));
    }

    @Test
    public void accessTags() {
        assertNotNull(roomToTest.accessTags());
    }

    @Test
    public void accessCoordinates() {
        assertNotNull(roomToTest.accessCoordinates());
    }

    @Test
    public void changeCoordinates() {
        Point nPoint = new Point(2, 3);
        roomToTest.changeCoordinates(nPoint);

        assertEquals(nPoint,
                roomToTest.accessCoordinates());
    }

    @Test
    public void deleteRoom() {
        AreaPort area = context.createArea("A1");
        roomToTest = area.createRoom(1, 2);
        HintPort hint = roomToTest.createHint("H1");
        QuestPort q1 = roomToTest.createQuest("Q1");

        roomToTest.deleteRoom();

        assertFalse(roomToTest.accessQuests().contains(q1));
        assertFalse(
                roomToTest.accessHints().contains(hint));
        assertNull(hint.accessRoom());
        assertFalse(
                q1.accessQuestRooms().contains(roomToTest));
        assertFalse(
                area.accessRooms().contains(roomToTest));
        assertFalse(
                context.accessEveryHint().contains(hint));
    }
}