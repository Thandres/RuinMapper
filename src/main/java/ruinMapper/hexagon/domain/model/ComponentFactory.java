package ruinMapper.hexagon.domain.model;

import ruinMapper.hexagon.domain.area.Area;
import ruinMapper.hexagon.domain.area.AreaPort;
import ruinMapper.hexagon.domain.context.Context;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.hint.Hint;
import ruinMapper.hexagon.domain.quest.Quest;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.Room;
import ruinMapper.hexagon.domain.room.RoomPort;
import ruinMapper.hexagon.domain.tag.Tag;

import java.awt.*;
import java.util.UUID;

public class ComponentFactory implements
        ContextSupplierPort {

    private static CRUDRepositoryPort<Quest> questRepository;
    private static CRUDRepositoryPort<Room> roomRepository;
    private static CRUDRepositoryPort<Hint> hintRepository;
    private static CRUDRepositoryPort<Tag> tagRepository;
    private static CRUDRepositoryPort<Area> areaRepository;
    private static CRUDRepositoryPort<Context> contextRepository;


    private static ContextPort currentContext;

    public ComponentFactory(
            CRUDRepositoryPort<Quest> questRepository,
            CRUDRepositoryPort<Room> roomRepository,
            CRUDRepositoryPort<Hint> hintRepository,
            CRUDRepositoryPort<Tag> tagRepository,
            CRUDRepositoryPort<Area> areaRepository,
            CRUDRepositoryPort<Context> contextRepository) {

        this.questRepository = questRepository;
        this.roomRepository = roomRepository;
        this.hintRepository = hintRepository;
        this.tagRepository = tagRepository;
        this.areaRepository = areaRepository;
        this.contextRepository = contextRepository;

    }

    public static Quest createQuest(String title) {
        Quest newQuest = new Quest(title,
                currentContext,
                questRepository);
        questRepository.create(newQuest);
        return newQuest;
    }

    public static Hint createHint(String content,
                                  RoomPort room) {
        Hint newHint = new Hint(content,
                room,
                hintRepository);
        hintRepository.create(newHint);
        return newHint;
    }

    public static Tag createTag(String type) {
        Tag newTag = new Tag(type,
                currentContext, tagRepository,
                UUID.randomUUID());
        tagRepository.create(newTag);
        return newTag;
    }

    public static Area createArea(String title) {
        Area newArea = new Area(title,
                currentContext, areaRepository);
        areaRepository.create(newArea);
        return newArea;
    }

    public static Room createRoom(Point point,
                                  AreaPort area) {
        Room newRoom = new Room(point,
                currentContext, area, roomRepository);
        roomRepository.create(newRoom);
        return newRoom;
    }

    public static Context createContext(String name) {
        Context newContext = new Context(name,
                contextRepository,
                UUID.randomUUID());
        contextRepository.create(newContext);
        return newContext;
    }


    @Override
    public ContextPort createNewContext(String name) {
        Context context = createContext(name);
        ComponentFactory.currentContext = context;
        contextRepository.create(context);
        return context;
    }

    @Override
    public ContextPort loadContextByName(String name) {
        Context loaded = contextRepository.read(name);

        return loaded;
    }
}
