package ruinMapper.hexagon.domain.model;

import ruinMapper.hexagon.domain.area.Area;
import ruinMapper.hexagon.domain.context.Context;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.hint.Hint;
import ruinMapper.hexagon.domain.hint.HintStatus;
import ruinMapper.hexagon.domain.invariant.AreaDelegate;
import ruinMapper.hexagon.domain.invariant.InvariantKeeper;
import ruinMapper.hexagon.domain.invariant.RoomAndQuestAndHintDelegate;
import ruinMapper.hexagon.domain.invariant.TagAndHasTagDelegate;
import ruinMapper.hexagon.domain.quest.Quest;
import ruinMapper.hexagon.domain.quest.QuestStatus;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.Room;
import ruinMapper.hexagon.domain.room.RoomPort;
import ruinMapper.hexagon.domain.tag.Tag;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class ComponentFactory implements
        ContextSupplierPort {

    private static CRUDRepositoryPort<Quest> questRepository;
    private static CRUDRepositoryPort<Room> roomRepository;
    private static CRUDRepositoryPort<Hint> hintRepository;
    private static CRUDRepositoryPort<Tag> tagRepository;
    private static CRUDRepositoryPort<Area> areaRepository;
    private static CRUDRepositoryPort<Context> contextRepository;
    private static CRUDRepositoryPort<InvariantKeeper> stateKeeperRepository;

    private static InvariantKeeper stateKeeper;


    public ComponentFactory(
            CRUDRepositoryPort<Quest> questRepository,
            CRUDRepositoryPort<Room> roomRepository,
            CRUDRepositoryPort<Hint> hintRepository,
            CRUDRepositoryPort<Tag> tagRepository,
            CRUDRepositoryPort<Area> areaRepository,
            CRUDRepositoryPort<Context> contextRepository,
            CRUDRepositoryPort<InvariantKeeper> stateKeeperRepository) {

        this.questRepository = questRepository;
        this.roomRepository = roomRepository;
        this.hintRepository = hintRepository;
        this.tagRepository = tagRepository;
        this.areaRepository = areaRepository;
        this.contextRepository = contextRepository;
        this.stateKeeperRepository = stateKeeperRepository;
    }

    public static Quest createQuest(String title) {
        Quest newQuest = new Quest(title,
                "",
                "", stateKeeper, QuestStatus.ACTIVE,
                questRepository,
                UUID.randomUUID()
        );
        questRepository.create(newQuest);
        return newQuest;
    }

    public static Hint createHint(String content,
                                  RoomPort room) {
        Hint newHint = new Hint(content,
                "", stateKeeper, HintStatus.NO_IDEA,
                hintRepository, UUID.randomUUID());
        hintRepository.create(newHint);
        return newHint;
    }

    public static Tag createTag(String type) {
        Tag newTag = new Tag(type,
                tagRepository, stateKeeper,
                UUID.randomUUID());
        tagRepository.create(newTag);
        return newTag;
    }

    public static Area createArea(String title) {
        Area newArea = new Area(title, "", new HashMap<>(),
                stateKeeper, areaRepository,
                UUID.randomUUID());
        areaRepository.create(newArea);
        return newArea;
    }

    public static Room createRoom(int x, int y) {
        Room newRoom = new Room("", "",
                new Point(x, y), stateKeeper, stateKeeper,
                stateKeeper, roomRepository,
                UUID.randomUUID());
        roomRepository.create(newRoom);
        return newRoom;
    }

    public static Context createContext(String name) {
        Context newContext = new Context(name,
                stateKeeper,
                stateKeeper, stateKeeper,
                stateKeeper.toString(), contextRepository,
                UUID.randomUUID());
        contextRepository.create(newContext);
        return newContext;
    }

    public static InvariantKeeper createStateKeeper() {
        InvariantKeeper stateKeeper = new InvariantKeeper(
                createRQDelegate(), createTTDelegate(),
                createAreaDelegate()
                , stateKeeperRepository,
                UUID.randomUUID());
        stateKeeperRepository.create(stateKeeper);
        return stateKeeper;
    }

    public static RoomAndQuestAndHintDelegate createRQDelegate() {
        return new RoomAndQuestAndHintDelegate(
                new HashMap<>(), new HashMap<>(),
                new HashMap<>(), new HashMap<>(),
                new HashSet<>());
    }

    public static TagAndHasTagDelegate createTTDelegate() {
        return new TagAndHasTagDelegate(new HashMap<>(),
                new HashSet<>());
    }

    public static AreaDelegate createAreaDelegate() {
        return new AreaDelegate(new HashSet<>());
    }

    @Override
    public ContextPort createNewContext(String name) {
        ComponentFactory.stateKeeper = createStateKeeper();
        return createContext(name);
    }

    @Override
    public ContextPort loadContextByName(String name) {
        Context loaded = contextRepository.read(name);
        ComponentFactory.stateKeeper = stateKeeperRepository
                .read(loaded.getStateKeeperID());
        return loaded;
    }
}
