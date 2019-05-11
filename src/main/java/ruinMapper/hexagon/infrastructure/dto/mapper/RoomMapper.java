package ruinMapper.hexagon.infrastructure.dto.mapper;

import ruinMapper.hexagon.domain.room.Room;
import ruinMapper.hexagon.infrastructure.dto.RoomDto;

public class RoomMapper implements
        DtoMapper<Room, RoomDto> {
    @Override
    public Room toDomain(RoomDto dto) {
        return null;
    }

    @Override
    public RoomDto toDto(Room domain) {
        RoomDto roomDto = new RoomDto();
        roomDto.setTitle(domain.accessTitle());
        roomDto.setNotes(domain.accessNotes());
        roomDto.setCooridnates(domain.accessCoordinates());
        roomDto.setRoomID(domain.toString());
        return roomDto;
    }
}
