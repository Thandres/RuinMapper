package ruinMapper.hexagon.domain;

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

public class DomainInjector implements
        ContextSupplierPort {

    private static CRUDRepositoryPort<Quest> questRepository;
    private static CRUDRepositoryPort<Room> roomRepository;
    private static CRUDRepositoryPort<Hint> hintRepository;
    private static CRUDRepositoryPort<Tag> tagRepository;
    private static CRUDRepositoryPort<Area> areaRepository;
    private static CRUDRepositoryPort<Context> contextRepository;
    private static ContextPort currentContext;

    public DomainInjector(
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
                currentContext.toString(),
                questRepository, roomRepository,
                contextRepository, UUID.randomUUID());
        questRepository.create(newQuest);
        return newQuest;
    }

    public static Hint createHint(String content,
                                  RoomPort room) {
        Hint newHint = new Hint(content,
                room.toString(), currentContext.toString(),
                hintRepository, contextRepository,
                roomRepository, tagRepository,
                UUID.randomUUID());
        hintRepository.create(newHint);
        return newHint;
    }

    public static Tag createTag(String type) {
        Tag newTag = new Tag(type,
                currentContext.toString(), tagRepository,
                contextRepository, UUID.randomUUID());
        tagRepository.create(newTag);
        return newTag;
    }

    public static Area createArea(String title) {
        Area newArea = new Area(title,
                currentContext.toString(),
                areaRepository, roomRepository,
                contextRepository, UUID.randomUUID());
        areaRepository.create(newArea);
        newArea.createRoom(0, 0);
        return newArea;
    }

    public static Room createRoom(Point point,
                                  AreaPort area) {
        Room newRoom = new Room(point,
                currentContext.toString(), roomRepository,
                hintRepository, questRepository,
                tagRepository, contextRepository,
                UUID.randomUUID());
        roomRepository.create(newRoom);
        return newRoom;
    }

    public static Context createContext(String name) {
        Context newContext = new Context(name,
                contextRepository, tagRepository,
                questRepository, areaRepository);
        currentContext = newContext;
        contextRepository.create(newContext);
        newContext.createArea("New Area");
        return newContext;
    }


    @Override
    public ContextPort createNewContext(String name) {
        Context context = createContext(name);
        DomainInjector.currentContext = context;
        contextRepository.create(context);
        return context;
    }

    @Override
    public ContextPort loadContextByName(String name) {
        Context loaded = contextRepository.read(name);
        currentContext = loaded;
        return loaded;
    }
}
