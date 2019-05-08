package ruinMapper.hexagon.infrastructure.dto.mapper;

import ruinMapper.hexagon.domain.area.Area;
import ruinMapper.hexagon.domain.room.RoomPort;
import ruinMapper.hexagon.infrastructure.DtoMapper;
import ruinMapper.hexagon.infrastructure.dto.AreaDto;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AreaMapper implements
        DtoMapper<Area, AreaDto> {


    @Override
    public Area toDomain(AreaDto dto) {

        return null;
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
