package ruinMapper.hexagon.domain.area;

import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.model.ComponentFactory;
import ruinMapper.hexagon.domain.model.ComponentSuper;
import ruinMapper.hexagon.domain.model.ComponentType;
import ruinMapper.hexagon.domain.model.HasRoom;
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
    private String notes;
    private Map<Point, RoomPort> areaMap;

    private ContextPort contextPort;

    private CRUDRepositoryPort<Area> areaRepository;
    private UUID areaID;

    public Area(String title,
                String notes,
                Map<Point, RoomPort> areaMap,
                ContextPort contextPort,
                CRUDRepositoryPort<Area> areaRepository,
                UUID areaID) {
        this.title = title;
        this.notes = notes;
        this.areaMap = areaMap;
        this.contextPort = contextPort;

        this.areaRepository = areaRepository;
        this.areaID = areaID;

        createRoom(0, 0);
    }


    @Override
    public RoomPort createRoom(int x, int y) {
        RoomPort newRoom = ComponentFactory
                .createRoom(new Point(x, y), this);
        areaMap.put(newRoom.accessCoordinates(), newRoom);
        saveState();
        return newRoom;
    }

    @Override
    public RoomPort accessRoom(int x, int y) {
        Point pointToRetrieve = new Point(x, y);
        return areaMap.get(pointToRetrieve);
    }

    @Override
    public Set<RoomPort> accessRooms() {
        return new HashSet<>(areaMap.values());
    }

    @Override
    public void deleteRoom(int x, int y) {
        Point point = new Point(x, y);
        if (areaMap.containsKey(point)) {
            RoomPort roomToDelete = areaMap.remove(point);
            roomToDelete.deleteRoom();
            saveState();
        }
    }

    @Override
    public void changeTitle(String newTitle) {
        title = newTitle;
        saveState();
    }

    @Override
    public String accessTitle() {
        return title;
    }

    @Override
    public void changeNotes(String newNotes) {
        notes = newNotes;
        saveState();
    }

    @Override
    public String accessNotes() {
        return notes;
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
    public void deleteArea() {
        if (contextPort != null) {
            ContextPort temp = contextPort;
            contextPort = null;
            Set<RoomPort> entries = new HashSet<>(
                    areaMap.values());
            for (RoomPort room : entries) {
                Point coordinates = room
                        .accessCoordinates();
                deleteRoom(coordinates.x, coordinates.y);
            }
            temp.deleteArea(this);
            areaRepository.delete(toString());
        }
    }

    @Override
    public void saveState() {
        areaRepository.update(this);
    }


    @Override
    public String toString() {
        return areaID.toString();
    }

    @Override
    public ComponentType getType() {
        return ComponentType.AREA;
    }
}
