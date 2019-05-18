package ruinMapper.hexagon.infrastructure.persistence.room;

import ruinMapper.hexagon.domain.area.Area;
import ruinMapper.hexagon.domain.context.Context;
import ruinMapper.hexagon.domain.hint.Hint;
import ruinMapper.hexagon.domain.quest.Quest;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.Room;
import ruinMapper.hexagon.domain.tag.Tag;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;

import java.awt.*;
import java.util.UUID;

public class RoomMapper implements
        DtoMapper<Room, RoomDto> {

    private CRUDRepositoryPort<Context> contextRepository;

    private CRUDRepositoryPort<Hint> hintRepository;
    private CRUDRepositoryPort<Quest> questRepository;
    private CRUDRepositoryPort<Tag> tagRepositroy;
    private CRUDRepositoryPort<Area> areaRepositroy;

    public RoomMapper(
            CRUDRepositoryPort<Context> contextRepository,
            CRUDRepositoryPort<Hint> hintRepository,
            CRUDRepositoryPort<Quest> questRepository,
            CRUDRepositoryPort<Tag> tagRepositroy,
            CRUDRepositoryPort<Area> areaRepositroy) {
        this.contextRepository = contextRepository;
        this.hintRepository = hintRepository;
        this.questRepository = questRepository;
        this.tagRepositroy = tagRepositroy;
        this.areaRepositroy = areaRepositroy;
    }

    @Override
    public Room toDomain(RoomDto dto,
                         CRUDRepositoryPort<Room> repository) {
        Point point = new Point(dto.getX(), dto.getY());
        Room domain = new Room(point,
                dto.getAreaID(), dto.getContextID(),
                repository, hintRepository, questRepository,
                tagRepositroy, contextRepository,
                areaRepositroy,
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
        roomDto.setTitle(domain.getTitle());
        roomDto.setNotes(domain.getNotes());
        roomDto.setHints(domain.getHints());
        roomDto.setQuests(
                domain.getQuests());
        roomDto.setTags(domain.getTags());
        roomDto.setContextID(
                domain.getContextID());
        roomDto.setAreaID(domain.getAreaID());
        roomDto.setX(domain.accessCoordinates().x);
        roomDto.setY(domain.accessCoordinates().y);
        roomDto.setRoomID(domain.toString());
        return roomDto;
    }
}
