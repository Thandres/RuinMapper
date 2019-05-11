package ruinMapper.hexagon.infrastructure.persistence.quest;

import ruinMapper.hexagon.domain.quest.Quest;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;
import ruinMapper.hexagon.infrastructure.persistence.FileHelper;
import ruinMapper.hexagon.infrastructure.persistence.RepositoryAdapter;

public class QuestRepository extends
        RepositoryAdapter implements
        CRUDRepositoryPort<Quest> {

    private DtoMapper<Quest, QuestDto> questMapper;

    public QuestRepository(String directoryPath,
                           DtoMapper<Quest, QuestDto> questMapper) {
        super(directoryPath);
        this.questMapper = questMapper;
    }

    @Override
    public void create(Quest object) {
        QuestDto questDto = questMapper.toDto(object);
        FileHelper.writeToFile(
                createFilelocation(questDto.getQuestID()),
                questDto);
    }

    @Override
    public Quest read(String ID) {
        QuestDto questDto = FileHelper
                .readFromFile(createFilelocation(ID),
                        QuestDto.class);
        return questMapper.toDomain(questDto, this);
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
        FileHelper.deleteFile(createFilelocation(ID));
    }
}
