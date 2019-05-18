package ruinMapper.hexagon.infrastructure.persistence.area;

import ruinMapper.hexagon.domain.area.Area;
import ruinMapper.hexagon.domain.context.Context;
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


    public AreaMapper(
            CRUDRepositoryPort<Context> contextRepository,
            CRUDRepositoryPort<Room> roomRepository) {
        this.contextRepository = contextRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public Area toDomain(AreaDto dto,
                         CRUDRepositoryPort<Area> repository) {
        Area domain = new Area(dto.getTitle(),
                dto.getContextID(),
                repository, roomRepository,
                contextRepository,
                UUID.fromString(dto.getAreaID()));

        Map<Point, String> roomMap = new HashMap<>();

        for (Map.Entry<String, String> entry : dto
                .getRoomMap().entrySet()) {
            Point coordinates = convertToPoint(
                    entry.getKey());
            roomMap.put(coordinates, entry.getValue());
        }

        domain.setAreaMap(roomMap);

        domain.setNotes(dto.getNotes());

        return domain;
    }

    @Override
    public AreaDto toDto(Area domain) {
        AreaDto dto = new AreaDto();
        dto.setTitle(domain.accessTitle());
        dto.setNotes(domain.accessNotes());
        Map<String, String> map = new HashMap<>();
        for (RoomPort room : domain.accessRooms()) {
            map.put(convertToString(
                    room.accessCoordinates()),
                    room.toString());
        }
        dto.setContextID(
                domain.getContextID());
        dto.setRoomMap(map);
        dto.setAreaID(domain.toString());
        return dto;
    }

    private String convertToString(Point coordinate) {
        return coordinate.x + "," + coordinate.y;
    }

    private Point convertToPoint(String pointString) {
        String[] coordinatesAsString = pointString
                .split(",");
        return new Point(
                Integer.valueOf(coordinatesAsString[0]),
                Integer.valueOf(coordinatesAsString[1]));
    }
}
