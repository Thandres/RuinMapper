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
    private CRUDRepositoryPort<Area> areaRepository;
    private UUID areaID;

    public Area(String title,
                String notes,
                Map<Point, RoomPort> areaMap,
                CRUDRepositoryPort<Area> areaRepository,
                UUID areaID) {
        this.title = title;
        this.notes = notes;
        this.areaMap = areaMap;
        this.areaRepository = areaRepository;
        this.areaID = areaID;
    }


    @Override
    public RoomPort createRoom(int x, int y) {
        RoomPort newRoom = ComponentFactory
                .createRoom(x, y);
        addRoom(newRoom, this);
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
        removeRoom(areaMap.get(point), this);
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
    public String toString() {
        return areaID.toString();
    }

    @Override
    public void addRoom(RoomPort value, HasRoom key) {
        areaMap.put(value.accessCoordinates(), value);
    }

    @Override
    public void removeRoom(RoomPort value, HasRoom key) {
        areaMap.remove(value.accessCoordinates());
    }

    @Override
    public Set<RoomPort> accessRooms(HasRoom hasRoom) {
        return new HashSet<>(areaMap.values());
    }

    @Override
    public <T extends ComponentTag> void deleteManagedObject(
            T managedObject) {

    }

    @Override
    public ComponentType getType() {
        return ComponentType.AREA;
    }
}
