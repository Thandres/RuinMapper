package ruinMapper.hexagon.domain.tag;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import ruinMapper.hexagon.domain.ContextSupplier;
import ruinMapper.hexagon.domain.ContextSupplierPort;
import ruinMapper.hexagon.domain.area.AreaPort;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.room.RoomPort;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TagPortTest {

    private ContextPort context;
    private ContextSupplierPort lifecyclePort;
    private static final String CONTEXT_NAME = "map";
    private static final String TEST_DIRECTORY = "D:\\Repos\\map";



    private TagPort tagToTest;

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
        tagToTest = context.createValidTag("Shop");
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
    public void accessTag() {
        // Basically getter, needs no testing
    }

    @Test
    public void changeTag() {
        String nString = "Dungeon";
        tagToTest.changeTag(nString);

        assertEquals(nString, tagToTest.accessTag());
    }

    @Test
    public void deleteTag() {
        AreaPort area = context.createArea("");
        RoomPort r1 = area.createRoom(1, 2);
        r1.tagRoom(tagToTest);
        RoomPort r2 = area.createRoom(2, 2);
        r2.tagRoom(tagToTest);

        tagToTest.deleteTag();

        assertFalse(context.accessValidTags()
                .contains(tagToTest));
        assertFalse(r1.accessTags().contains(tagToTest));
        assertFalse(r2.accessTags().contains(tagToTest));
    }
}