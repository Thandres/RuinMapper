package ruinMapper.hexagon.infrastructure.persistence.room;

import ruinMapper.hexagon.domain.area.Area;
import ruinMapper.hexagon.domain.context.Context;
import ruinMapper.hexagon.domain.hint.Hint;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.quest.Quest;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.Room;
import ruinMapper.hexagon.domain.tag.Tag;
import ruinMapper.hexagon.domain.tag.TagPort;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;

import java.util.Set;
import java.util.UUID;

import static ruinMapper.hexagon.infrastructure.persistence.MappingHelper.toDomainSet;
import static ruinMapper.hexagon.infrastructure.persistence.MappingHelper.toStringSet;

public class RoomMapper implements
        DtoMapper<Room, RoomDto> {

    private CRUDRepositoryPort<Context> contextRepository;
    private CRUDRepositoryPort<Area> areaRepository;
    private CRUDRepositoryPort<Hint> hintRepository;
    private CRUDRepositoryPort<Quest> questRepository;
    private CRUDRepositoryPort<Tag> tagRepositroy;

    @Override
    public Room toDomain(RoomDto dto,
                         CRUDRepositoryPort<Room> repository) {
        Room domain = new Room(dto.getCooridnates(),
                contextRepository.read(dto.getContextID()),
                areaRepository.read(dto.getAreaID()),
                repository);
        domain.setNotes(dto.getNotes());
        domain.setTitle(dto.getTitle());
        Set<HintPort> hints = toDomainSet(dto.getHints(),
                hintRepository);
        domain.setHints(hints);
        Set<QuestPort> quests = toDomainSet(
                dto.getQuests(), questRepository);
        domain.setQuests(quests);
        Set<TagPort> tags = toDomainSet(dto.getTags(),
                tagRepositroy);
        domain.setTags(tags);
        domain.setRoomID(UUID.fromString(dto.getRoomID()));
        return domain;
    }

    @Override
    public RoomDto toDto(Room domain) {
        RoomDto roomDto = new RoomDto();
        roomDto.setTitle(domain.accessTitle());
        roomDto.setNotes(domain.accessNotes());
        roomDto.setHints(toStringSet(domain.accessHints()));
        roomDto.setQuests(
                toStringSet(domain.accessQuests()));
        roomDto.setTags(toStringSet(domain.accessTags()));
        roomDto.setAreaID(domain.getArea().toString());
        roomDto.setContextID(
                domain.getContext().toString());
        roomDto.setCooridnates(domain.accessCoordinates());
        roomDto.setRoomID(domain.toString());
        return roomDto;
    }
}
