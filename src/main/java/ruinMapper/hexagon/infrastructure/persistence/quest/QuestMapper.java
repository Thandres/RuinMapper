package ruinMapper.hexagon.infrastructure.persistence.quest;

import ruinMapper.hexagon.domain.context.Context;
import ruinMapper.hexagon.domain.quest.Quest;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.Room;
import ruinMapper.hexagon.domain.room.RoomPort;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;
import ruinMapper.hexagon.infrastructure.persistence.MappingHelper;

import java.util.Set;
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
                contextRepository.read(dto.getContextID()),
                repository);
        domain.setDescription(dto.getDescription());
        domain.setNotes(dto.getNotes());
        Set<RoomPort> rooms = MappingHelper
                .toDomainSet(dto.getRooms(),
                        roomRepository);
        domain.setRooms(rooms);
        domain.setStatus(dto.getStatus());
        domain.setQuestID(
                UUID.fromString(dto.getQuestID()));
        return domain;
    }

    @Override
    public QuestDto toDto(Quest domain) {
        QuestDto questDto = new QuestDto();
        questDto.setTitle(domain.accessTitle());
        questDto.setDescription(domain.accessDescription());
        questDto.setRooms(MappingHelper
                .toStringSet(domain.getRooms()));
        questDto.setNotes(domain.accessNotes());
        questDto.setStatus(domain.accessQuestStatus());
        questDto.setQuestID(domain.toString());
        return questDto;
    }
}
