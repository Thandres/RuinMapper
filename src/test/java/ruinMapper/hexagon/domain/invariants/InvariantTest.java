package ruinMapper.hexagon.domain.invariants;

import org.junit.Before;
import org.junit.Test;
import ruinMapper.fixtures.*;
import ruinMapper.hexagon.ComponentFactory;
import ruinMapper.hexagon.domain.Taggable;
import ruinMapper.hexagon.domain.TaggableManager;
import ruinMapper.hexagon.domain.area.AreaPort;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.room.RoomPort;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InvariantTest {

    private ContextPort context;

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
        context = ComponentFactory.createContext();

    }


    @Test
    public void tagCreationInvariantTest() {
        TagPort tag1 = context.createTag("tag1");
        assertTrue(context.accessTags().contains(tag1));
        assertTrue(
                tag1.getTaggedObjects().contains(context));
    }

    @Test
    public void taggableConractTest() {
        TagPort tag = context.createTag("tag1");
        TaggableManager taggableManager = tag;
        Taggable tContext = context;

        tContext.removeTag(tag);
        assertFalse(tContext.accessTags().contains(tag));
        assertFalse(taggableManager.getTaggedObjects()
                .contains(tContext));

        tContext.addTag(tag);
        assertTrue(tContext.accessTags().contains(tag));
        assertTrue(taggableManager.getTaggedObjects()
                .contains(context));


        Taggable tRoom = context.createArea("")
                .createRoom(0, 0);

        tRoom.addTag(tag);
        assertTrue(tContext.accessTags().contains(tag));
        assertTrue(taggableManager.getTaggedObjects()
                .contains(context));

        tRoom.removeTag(tag);
        assertFalse(tRoom.accessTags().contains(tag));
        assertFalse(taggableManager.getTaggedObjects()
                .contains(tRoom));

    }

    @Test
    public void tagDeletionInvariantTest() {
        TagPort tag1 = context.createTag("tag1");
        TagPort tag2 = context.createTag("tag2");
        TagPort tag3 = context.createTag("tag3");
        AreaPort area = context.createArea("area1");
        List<RoomPort> rooms = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            area.createRoom(0, i).addTag(tag1);
            area.createRoom(i, 0).addTag(tag2);
            area.createRoom(i, i).addTag(tag3);
        }


        context.deleteTag(tag1);
        tag2.deleteTag();
        context.removeTag(tag3);

        assertFalse(context.accessTags().contains(tag1));
        for (RoomPort room : rooms) {
            assertFalse(room.accessHints().contains(tag1));
            assertFalse(room.accessHints().contains(tag2));
            assertFalse(room.accessHints().contains(tag3));
        }
        assertFalse(context.accessTags().contains(tag2));
    }
}
