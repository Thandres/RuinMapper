package ruinMapper.hexagon.infrastructure.persistence.area;

import ruinMapper.hexagon.domain.area.Area;
import ruinMapper.hexagon.domain.context.Context;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.Room;
import ruinMapper.hexagon.domain.room.RoomPort;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AreaMapper implements
        DtoMapper<Area, AreaDto> {


    private CRUDRepositoryPort<Context> contextRepository;
    private CRUDRepositoryPort<Room> roomRepository;
    private CRUDRepositoryPort<Area> areaRepository;

    public AreaMapper(
            CRUDRepositoryPort<Context> contextRepository,
            CRUDRepositoryPort<Room> roomRepository,
            CRUDRepositoryPort<Area> areaRepository) {
        this.contextRepository = contextRepository;
        this.roomRepository = roomRepository;
        this.areaRepository = areaRepository;
    }

    @Override
    public Area toDomain(AreaDto dto) {
        ContextPort contextPort = contextRepository
                .read(dto.getContextID());
        Map<Point, RoomPort> roomMap = new HashMap<>();
        for (String roomID : dto.getRoomMap().values()) {
            RoomPort room = roomRepository.read(roomID);
            roomMap.put(room.accessCoordinates(), room);
        }

        Area domain = new Area(dto.getTitle(), contextPort,
                areaRepository);
        domain.setAreaID(UUID.fromString(dto.getAreaID()));
        domain.setAreaMap(roomMap);

        domain.setNotes(dto.getNotes());

        return domain;
    }

    @Override
    public AreaDto toDto(Area domain) {
        AreaDto dto = new AreaDto();
        dto.setTitle(domain.accessTitle());
        dto.setNotes(domain.accessNotes());
        Map<Point, String> map = new HashMap<>();
        for (RoomPort room : domain.accessRooms()) {
            map.put(room.accessCoordinates(),
                    room.toString());
        }
        dto.setRoomMap(map);
        dto.setAreaID(domain.toString());
        return dto;
    }

}
