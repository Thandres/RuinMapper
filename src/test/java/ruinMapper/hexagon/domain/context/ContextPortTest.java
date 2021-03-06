package ruinMapper.hexagon.domain.context;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import ruinMapper.hexagon.domain.ContextSupplier;
import ruinMapper.hexagon.domain.ContextSupplierPort;
import ruinMapper.hexagon.domain.area.AreaPort;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.room.RoomPort;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.io.File;
import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.*;

public class ContextPortTest {
    private ContextPort contextToTest;

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
        contextToTest = lifecyclePort
                .createNewContext(CONTEXT_NAME);

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

    @Test
    public void createKeyword() {
        TagPort keyword = contextToTest
                .createKeyword("K1");

        assertTrue(contextToTest.accessEveryKeyWord()
                .contains(keyword));
    }

    @Test
    public void deleteKeyWord() {
        TagPort keywordToDelete = contextToTest
                .createKeyword("K1");
        HintPort taggedHint = contextToTest.createArea("A1")
                .createRoom(0, 2).createHint("H1");
        taggedHint.addKeyWord(keywordToDelete);

        contextToTest.deleteKeyword(keywordToDelete);

        assertFalse(contextToTest.accessEveryKeyWord()
                .contains(keywordToDelete));
        assertFalse(taggedHint.accessKeyWords()
                .contains(keywordToDelete));
    }

    @Test
    public void accessKeywords() {
        assertNotNull(contextToTest.accessEveryKeyWord());
    }

}