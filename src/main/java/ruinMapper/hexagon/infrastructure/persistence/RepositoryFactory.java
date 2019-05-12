package ruinMapper.hexagon.infrastructure.persistence;

import ruinMapper.hexagon.infrastructure.persistence.area.AreaMapper;
import ruinMapper.hexagon.infrastructure.persistence.area.AreaRepository;
import ruinMapper.hexagon.infrastructure.persistence.context.ContextMapper;
import ruinMapper.hexagon.infrastructure.persistence.context.ContextRepository;
import ruinMapper.hexagon.infrastructure.persistence.hint.HintMapper;
import ruinMapper.hexagon.infrastructure.persistence.hint.HintRepository;
import ruinMapper.hexagon.infrastructure.persistence.quest.QuestMapper;
import ruinMapper.hexagon.infrastructure.persistence.quest.QuestRepository;
import ruinMapper.hexagon.infrastructure.persistence.room.RoomMapper;
import ruinMapper.hexagon.infrastructure.persistence.room.RoomRepository;
import ruinMapper.hexagon.infrastructure.persistence.tag.TagMapper;
import ruinMapper.hexagon.infrastructure.persistence.tag.TagRepository;

public class RepositoryFactory {

    private static QuestRepository questRepository;
    private static QuestMapper questMapper;

    private static TagRepository tagRepository;
    private static TagMapper tagMapper;

    private static AreaRepository areaRepository;
    private static AreaMapper areaMapper;

    private static ContextRepository contextRepository;
    private static ContextMapper contextMapper;

    private static HintRepository hintRepository;
    private static HintMapper hintMapper;

    private static RoomRepository roomRepository;
    private static RoomMapper roomMapper;

    public static void prepareRepositories(
            String directoryPath) {
        questRepository = new QuestRepository(
                directoryPath);
        tagRepository = new TagRepository(directoryPath);
        areaRepository = new AreaRepository(directoryPath);
        contextRepository = new ContextRepository(
                directoryPath);
        hintRepository = new HintRepository(directoryPath);
        roomRepository = new RoomRepository(directoryPath);

        questMapper = new QuestMapper(roomRepository,
                contextRepository);
        questRepository.setQuestMapper(questMapper);

        tagMapper = new TagMapper(contextRepository);
        tagRepository.setTagMapper(tagMapper);

        areaMapper = new AreaMapper(contextRepository,
                roomRepository);
        areaRepository.setAreaMapper(areaMapper);

        contextMapper = new ContextMapper(areaRepository,
                tagRepository, questRepository);
        contextRepository.setContextMapper(contextMapper);

        hintMapper = new HintMapper(roomRepository,
                contextRepository, tagRepository);
        hintRepository.setHintMapper(hintMapper);

        roomMapper = new RoomMapper(contextRepository,
                areaRepository, hintRepository,
                questRepository, tagRepository);
        roomRepository.setRoomMapper(roomMapper);
    }

    public static QuestRepository getQuestRepository() {
        return questRepository;
    }

    public static void setQuestRepository(
            QuestRepository questRepository) {
        RepositoryFactory.questRepository = questRepository;
    }

    public static TagRepository getTagRepository() {
        return tagRepository;
    }

    public static void setTagRepository(
            TagRepository tagRepository) {
        RepositoryFactory.tagRepository = tagRepository;
    }

    public static AreaRepository getAreaRepository() {
        return areaRepository;
    }

    public static void setAreaRepository(
            AreaRepository areaRepository) {
        RepositoryFactory.areaRepository = areaRepository;
    }

    public static ContextRepository getContextRepository() {
        return contextRepository;
    }

    public static void setContextRepository(
            ContextRepository contextRepository) {
        RepositoryFactory.contextRepository = contextRepository;
    }

    public static HintRepository getHintRepository() {
        return hintRepository;
    }

    public static void setHintRepository(
            HintRepository hintRepository) {
        RepositoryFactory.hintRepository = hintRepository;
    }

    public static RoomRepository getRoomRepository() {
        return roomRepository;
    }

    public static void setRoomRepository(
            RoomRepository roomRepository) {
        RepositoryFactory.roomRepository = roomRepository;
    }
}
