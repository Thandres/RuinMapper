package ruinMapper.hexagon.infrastructure.persistence.room;

import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.Room;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;
import ruinMapper.hexagon.infrastructure.persistence.FileHelper;
import ruinMapper.hexagon.infrastructure.persistence.RepositoryAdapter;

public class RoomRepository extends
        RepositoryAdapter implements
        CRUDRepositoryPort<Room> {

    private DtoMapper<Room, RoomDto> roomMapper;

    public RoomRepository(String directoryPath,
                          DtoMapper<Room, RoomDto> roomMapper) {
        super(directoryPath);
        this.roomMapper = roomMapper;
    }

    @Override
    public void create(Room object) {
        RoomDto roomDto = roomMapper.toDto(object);
        FileHelper.writeToFile(
                createFilelocation(roomDto.getRoomID()),
                roomDto);
    }

    @Override
    public Room read(String ID) {
        RoomDto roomDto = FileHelper
                .readFromFile(createFilelocation(ID),
                        RoomDto.class);
        return roomMapper.toDomain(roomDto, this);
    }

    @Override
    public void update(Room object) {
        RoomDto roomDto = roomMapper.toDto(object);
        FileHelper.writeToFile(
                createFilelocation(roomDto.getRoomID()),
                roomDto);
    }

    @Override
    public void delete(String ID) {
        FileHelper.deleteFile(createFilelocation(ID));
    }
}
