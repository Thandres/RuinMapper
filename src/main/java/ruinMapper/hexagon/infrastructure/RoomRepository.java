package ruinMapper.hexagon.infrastructure;

import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.Room;

public class RoomRepository implements
        CRUDRepositoryPort<Room> {
    @Override
    public void create(Room object) {

    }

    @Override
    public Room read(String ID) {
        return null;
    }

    @Override
    public void update(Room object) {

    }

    @Override
    public void delete(String ID) {

    }
}
