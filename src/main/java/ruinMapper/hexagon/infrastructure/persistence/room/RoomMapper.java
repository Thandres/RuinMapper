package ruinMapper.hexagon.infrastructure.persistence.room;

import ruinMapper.hexagon.domain.context.Context;
import ruinMapper.hexagon.domain.hint.Hint;
import ruinMapper.hexagon.domain.quest.Quest;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.Room;
import ruinMapper.hexagon.domain.tag.Tag;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;

import java.awt.*;
import java.util.UUID;

import static ruinMapper.hexagon.infrastructure.persistence.MappingHelper.toStringSet;

public class RoomMapper implements
        DtoMapper<Room, RoomDto> {

    private CRUDRepositoryPort<Context> contextRepository;

    private CRUDRepositoryPort<Hint> hintRepository;
    private CRUDRepositoryPort<Quest> questRepository;
    private CRUDRepositoryPort<Tag> tagRepositroy;

    public RoomMapper(
            CRUDRepositoryPort<Context> contextRepository,
            CRUDRepositoryPort<Hint> hintRepository,
            CRUDRepositoryPort<Quest> questRepository,
            CRUDRepositoryPort<Tag> tagRepositroy) {
        this.contextRepository = contextRepository;
        this.hintRepository = hintRepository;
        this.questRepository = questRepository;
        this.tagRepositroy = tagRepositroy;
    }

    @Override
    public Room toDomain(RoomDto dto,
                         CRUDRepositoryPort<Room> repository) {
        Point point = new Point(dto.getX(), dto.getY());
        Room domain = new Room(point,
                dto.getContextID(),
                repository, hintRepository, questRepository,
                tagRepositroy, contextRepository,
                UUID.fromString(dto.getRoomID()));
        domain.setNotes(dto.getNotes());
        domain.setTitle(dto.getTitle());
        domain.setHints(dto.getHints());
        domain.setQuests(dto.getQuests());
        domain.setTags(dto.getTags());
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
        roomDto.setContextID(
                domain.getContextID().toString());
        roomDto.setX(domain.accessCoordinates().x);
        roomDto.setY(domain.accessCoordinates().y);
        roomDto.setRoomID(domain.toString());
        return roomDto;
    }
}
