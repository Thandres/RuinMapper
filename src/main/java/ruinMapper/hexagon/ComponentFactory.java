package ruinMapper.hexagon;

import ruinMapper.hexagon.domain.InvariantKeeper;
import ruinMapper.hexagon.domain.RoomAndQuestDelegate;
import ruinMapper.hexagon.domain.TagAndHasTagDelegate;
import ruinMapper.hexagon.domain.area.Area;
import ruinMapper.hexagon.domain.context.Context;
import ruinMapper.hexagon.domain.hint.Hint;
import ruinMapper.hexagon.domain.hint.HintStatus;
import ruinMapper.hexagon.domain.quest.Quest;
import ruinMapper.hexagon.domain.quest.QuestStatus;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.Room;
import ruinMapper.hexagon.domain.room.RoomPort;
import ruinMapper.hexagon.domain.tag.Tag;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class ComponentFactory {

    private static CRUDRepositoryPort<Quest> questRepository;
    private static CRUDRepositoryPort<Room> roomRepository;
    private static CRUDRepositoryPort<Hint> hintRepository;
    private static CRUDRepositoryPort<Tag> tagRepository;
    private static CRUDRepositoryPort<Area> areaRepository;
    private static CRUDRepositoryPort<Context> contextRepository;
    private static CRUDRepositoryPort<InvariantKeeper> stateKeeperRepository;

    private static InvariantKeeper stateKeeper;

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
                "", room, HintStatus.NO_IDEA,
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
        Area newArea = new Area(title, new HashMap<>(),
                areaRepository, UUID.randomUUID());
        areaRepository.create(newArea);
        return newArea;
    }

    public static Room createRoom() {
        Room newRoom = new Room("", "",
                new HashSet<>(), stateKeeper,
                stateKeeper, roomRepository,
                UUID.randomUUID());
        roomRepository.create(newRoom);
        return newRoom;
    }

    public static Context createContext() {
        Context newContext = new Context(new HashSet<>(),
                stateKeeper, stateKeeper,
                contextRepository, UUID.randomUUID());
        contextRepository.create(newContext);
        return newContext;
    }

    public static InvariantKeeper createStateKeeper() {
        InvariantKeeper stateKeeper = new InvariantKeeper(
                createRQDelegate(), createTTDelegate(),
                stateKeeperRepository,
                UUID.randomUUID());
        stateKeeperRepository.create(stateKeeper);
        return stateKeeper;
    }

    public static RoomAndQuestDelegate createRQDelegate() {
        return new RoomAndQuestDelegate(
                new HashMap<>(), new HashMap<>(),
                new HashSet<>());
    }

    public static TagAndHasTagDelegate createTTDelegate() {
        return new TagAndHasTagDelegate(new HashMap<>(),
                new HashSet<>());
    }

    public static void setQuestRepository(
            CRUDRepositoryPort<Quest> questRepository) {
        ComponentFactory.questRepository = questRepository;
    }


    public static void setHintRepository(
            CRUDRepositoryPort<Hint> hintRepository) {
        ComponentFactory.hintRepository = hintRepository;
    }

    public static void setTagRepository(
            CRUDRepositoryPort<Tag> tagRepository) {
        ComponentFactory.tagRepository = tagRepository;
    }

    public static void setAreaRepository(
            CRUDRepositoryPort<Area> areaRepository) {
        ComponentFactory.areaRepository = areaRepository;
    }

    public static void setRoomRepository(
            CRUDRepositoryPort<Room> roomRepository) {
        ComponentFactory.roomRepository = roomRepository;
    }

    public static void setContextRepository(
            CRUDRepositoryPort<Context> contextRepository) {
        ComponentFactory.contextRepository = contextRepository;
    }

    public static void setStateKeeperRepository(
            CRUDRepositoryPort<InvariantKeeper> stateKeeperRepository) {
        ComponentFactory.stateKeeperRepository = stateKeeperRepository;
    }

    // TODO replace this Setter with repository.read
    public static void setStateKeeper(
            InvariantKeeper stateKeeper) {
        ComponentFactory.stateKeeper = stateKeeper;
    }
}
