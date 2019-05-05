package ruinMapper.hexagon.domain.area;

import ruinMapper.hexagon.ComponentFactory;
import ruinMapper.hexagon.domain.ComponentSuper;
import ruinMapper.hexagon.domain.ComponentType;
import ruinMapper.hexagon.domain.HasRoom;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.RoomPort;

import java.awt.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Area extends ComponentSuper implements
        AreaPort, HasRoom {
    private String title;
    private Map<Point, RoomPort> areaMap;
    private CRUDRepositoryPort<Area> areaRepository;
    private UUID areaID;

    public Area(String title,
                Map<Point, RoomPort> areaMap,
                CRUDRepositoryPort<Area> areaRepository,
                UUID areaID) {
        this.title = title;
        this.areaMap = areaMap;
        this.areaRepository = areaRepository;
        this.areaID = areaID;
    }


    @Override
    public RoomPort createRoom(int x, int y) {
        RoomPort newRoom = ComponentFactory.createRoom();
        areaMap.put(new Point(x, y), newRoom);
        saveState();
        return newRoom;
    }

    @Override
    public RoomPort accessRoom(int x, int y) {
        return areaMap.get(new Point(x, y));
    }

    @Override
    public Set<RoomPort> accessRooms() {
        return new HashSet<>(areaMap.values());
    }

    @Override
    public void deleteRoom(int x, int y) {
        Point point = new Point(x, y);
        if (areaMap.containsKey(point)) {
            areaMap.remove(point);
            saveState();
        }
    }

    @Override
    public void changeTitle(String newTitle) {
        title = newTitle;
    }

    @Override
    public String accessTitle() {
        return title;
    }

    @Override
    public Set<HintPort> accessHintsOnArea() {
        Set<HintPort> hintSet = new HashSet<>();
        for (RoomPort room : areaMap.values()) {
            hintSet.addAll(room.accessHints());
        }
        return hintSet;
    }

    @Override
    public void saveState() {
        areaRepository.update(this);
    }

    @Override
    public ComponentType getType() {
        return ComponentType.AREA;
    }

    @Override
    public String toString() {
        return areaID.toString();
    }
}
