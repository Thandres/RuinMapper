package ruinMapper.hexagon.infrastructure.persistence.room;

import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.Room;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;
import ruinMapper.hexagon.infrastructure.persistence.FileHelper;
import ruinMapper.hexagon.infrastructure.persistence.RepositoryAdapter;

import java.util.HashMap;
import java.util.Map;

public class RoomRepository extends
        RepositoryAdapter implements
        CRUDRepositoryPort<Room> {

    private DtoMapper<Room, RoomDto> roomMapper;
    private Map<String, Room> loadedRooms;

    public RoomRepository(String directoryPath) {
        super(directoryPath);
        loadedRooms = new HashMap<>();
    }

    @Override
    public void create(Room object) {
        loadedRooms.put(object.toString(), object);
        RoomDto roomDto = roomMapper.toDto(object);
        FileHelper.writeToFile(
                createFilelocation(roomDto.toString()),
                roomDto);
    }

    @Override
    public Room read(String ID) {
        if (loadedRooms.containsKey(ID)) {
            return loadedRooms.get(ID);
        } else {

            RoomDto roomDto = FileHelper
                    .readFromFile(createFilelocation(ID),
                            RoomDto.class);
            Room loadedRoom = roomMapper
                    .toDomain(roomDto, this);
            loadedRooms
                    .put(loadedRoom.toString(), loadedRoom);
            return loadedRoom;
        }
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
        if (loadedRooms.containsKey(ID)) {
            loadedRooms.remove(ID);
        }
        FileHelper.deleteFile(createFilelocation(ID));
    }

    public void setRoomMapper(
            DtoMapper<Room, RoomDto> roomMapper) {
        this.roomMapper = roomMapper;
    }
}
