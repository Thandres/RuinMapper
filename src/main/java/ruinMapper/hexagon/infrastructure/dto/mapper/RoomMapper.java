package ruinMapper.hexagon.infrastructure.dto.mapper;

import ruinMapper.hexagon.domain.room.Room;
import ruinMapper.hexagon.infrastructure.DtoMapper;
import ruinMapper.hexagon.infrastructure.dto.RoomDto;

public class RoomMapper implements
        DtoMapper<Room, RoomDto> {
    @Override
    public Room toDomain(RoomDto dto) {
        return null;
    }

    @Override
    public RoomDto toDto(Room domain) {
        return null;
    }
}
