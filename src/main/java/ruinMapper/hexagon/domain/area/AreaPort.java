package ruinMapper.hexagon.domain.area;

import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.room.RoomPort;

import java.util.Set;

/**
 * Keeps track of rooms and all hints on the area
 */
public interface AreaPort {
    public RoomPort createRoom(int x, int y);

    public RoomPort accessRoom(int x, int y);

    public Set<RoomPort> accessRooms();

    public void deleteRoom(int x, int y);

    public void changeTitle(String newTitle);

    public String accessTitle();

    public Set<HintPort> accessHintsOnArea();
}
