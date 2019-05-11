package ruinMapper.hexagon.infrastructure.persistence.mapper;

import ruinMapper.hexagon.domain.quest.Quest;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;
import ruinMapper.hexagon.infrastructure.persistence.QuestDto;

public class QuestMapper implements
        DtoMapper<Quest, QuestDto> {
    @Override
    public Quest toDomain(QuestDto dto) {
        return null;
    }

    @Override
    public QuestDto toDto(Quest domain) {
        QuestDto questDto = new QuestDto();
        questDto.setTitle(domain.accessTitle());
        questDto.setDescription(domain.accessDescription());
        questDto.setNotes(domain.accessNotes());
        questDto.setStatus(domain.accessQuestStatus());
        questDto.setQuestID(domain.toString());
        return questDto;
    }
}