package ruinMapper.hexagon.infrastructure.persistence.quest;

import ruinMapper.hexagon.domain.quest.Quest;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;
import ruinMapper.hexagon.infrastructure.persistence.FileHelper;
import ruinMapper.hexagon.infrastructure.persistence.RepositoryAdapter;

import java.util.HashMap;
import java.util.Map;

public class QuestRepository extends
        RepositoryAdapter implements
        CRUDRepositoryPort<Quest> {

    private DtoMapper<Quest, QuestDto> questMapper;
    private Map<String, Quest> loadedQuests;

    public QuestRepository(String directoryPath,
                           DtoMapper<Quest, QuestDto> questMapper) {
        super(directoryPath);
        this.questMapper = questMapper;
        loadedQuests = new HashMap<>();
    }

    @Override
    public void create(Quest object) {
        loadedQuests.put(object.toString(), object);
        QuestDto questDto = questMapper.toDto(object);
        FileHelper.writeToFile(
                createFilelocation(questDto.getQuestID()),
                questDto);
    }

    @Override
    public Quest read(String ID) {
        if (loadedQuests.containsKey(ID)) {
            return loadedQuests.get(ID);
        } else {
            QuestDto questDto = FileHelper
                    .readFromFile(createFilelocation(ID),
                            QuestDto.class);
            Quest loadedQuest = questMapper
                    .toDomain(questDto, this);
            loadedQuests.put(loadedQuest.toString(),
                    loadedQuest);
            return loadedQuest;
        }
    }

    @Override
    public void update(Quest object) {
        QuestDto questDto = questMapper.toDto(object);
        FileHelper.writeToFile(
                createFilelocation(questDto.getQuestID()),
                questDto);
    }

    @Override
    public void delete(String ID) {
        if (loadedQuests.containsKey(ID)) {
            loadedQuests.remove(ID);
        }
        FileHelper.deleteFile(createFilelocation(ID));
    }
}
