package ruinMapper.hexagon.domain.area;

import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.invariant.RoomManager;
import ruinMapper.hexagon.domain.model.*;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.RoomPort;

import java.awt.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Area extends ComponentSuper implements
        AreaPort, RoomManager, HasRoom {
    private String title;
    private String notes;
    private Map<Point, RoomPort> areaMap;
    private RoomManager roomManager;
    private ComponentManager componentManager;
    private CRUDRepositoryPort<Area> areaRepository;
    private UUID areaID;

    public Area(String title,
                String notes,
                Map<Point, RoomPort> areaMap,
                ComponentManager componentManager,
                CRUDRepositoryPort<Area> areaRepository,
                UUID areaID) {
        this.title = title;
        this.notes = notes;
        this.areaMap = areaMap;
        this.componentManager = componentManager;
        this.areaRepository = areaRepository;
        this.areaID = areaID;

        roomManager = this;
        createRoom(0, 0);
    }


    @Override
    public RoomPort createRoom(int x, int y) {
        RoomPort newRoom = ComponentFactory
                .createRoom(x, y);
        roomManager.addRoom(newRoom, this);
        saveState();
        return newRoom;
    }

    @Override
    public RoomPort accessRoom(int x, int y) {
        Point pointToRetrieve = new Point(x, y);
        for (RoomPort room : roomManager
                .accessRooms(this)) {
            if (room.accessCoordinates()
                    .equals(pointToRetrieve)) {
                return room;
            }
        }
        return null; //TODO Exceptions
    }

    @Override
    public Set<RoomPort> accessRooms() {
        return roomManager.accessRooms(this);
    }

    @Override
    public void deleteRoom(int x, int y) {
        Point point = new Point(x, y);
        RoomPort roomToDelete = accessRoom(x, y);
        roomManager.removeRoom(roomToDelete, this);
        saveState();
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
        for (RoomPort room : roomManager
                .accessRooms(this)) {
            //TODO an object that has never added an object with a manager is not in the managers system, fix that
            hintSet.addAll(room.accessHints());
        }
        return hintSet;
    }

    @Override
    public void deleteArea() {
        roomManager.deleteManagedObject(this);
        areaRepository.delete(toString());
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
    public void addRoom(RoomPort value, HasRoom key) {
        areaMap.put(value.accessCoordinates(), value);
    }

    @Override
    public void removeRoom(RoomPort value, HasRoom key) {
        areaMap.remove(value.accessCoordinates())
                .deleteRoom();
    }

    @Override
    public Set<RoomPort> accessRooms(HasRoom hasRoom) {
        return new HashSet<>(areaMap.values());
    }

    @Override
    public <T extends ComponentTag> void deleteManagedObject(
            T managedObject) {
        componentManager.deleteManagedObject(this);

    }

    @Override
    public ComponentType getType() {
        return ComponentType.AREA;
    }
}
