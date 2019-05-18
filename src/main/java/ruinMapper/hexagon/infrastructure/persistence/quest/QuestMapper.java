package ruinMapper.hexagon.infrastructure.persistence.quest;

import ruinMapper.hexagon.domain.context.Context;
import ruinMapper.hexagon.domain.quest.Quest;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.Room;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;

import java.util.UUID;

public class QuestMapper implements
        DtoMapper<Quest, QuestDto> {

    private CRUDRepositoryPort<Room> roomRepository;
    private CRUDRepositoryPort<Context> contextRepository;

    public QuestMapper(
            CRUDRepositoryPort<Room> roomRepository,
            CRUDRepositoryPort<Context> contextRepository) {
        this.roomRepository = roomRepository;
        this.contextRepository = contextRepository;
    }

    @Override
    public Quest toDomain(QuestDto dto,
                          CRUDRepositoryPort<Quest> repository) {
        Quest domain = new Quest(dto.getTitle(),
                dto.getContextID(),
                repository, roomRepository,
                contextRepository,
                UUID.fromString(dto.getQuestID()));
        domain.setDescription(dto.getDescription());
        domain.setNotes(dto.getNotes());
        domain.setRooms(dto.getRooms());
        domain.setStatus(dto.getStatus());
        return domain;
    }

    @Override
    public QuestDto toDto(Quest domain) {
        QuestDto questDto = new QuestDto();
        questDto.setTitle(domain.getTitle());
        questDto.setDescription(domain.getDescription());
        questDto.setRooms(domain.getRooms());
        questDto.setNotes(domain.getNotes());
        questDto.setStatus(domain.getStatus());
        questDto.setQuestID(domain.toString());
        return questDto;
    }
}
