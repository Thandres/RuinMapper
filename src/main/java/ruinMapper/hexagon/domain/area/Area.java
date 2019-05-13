package ruinMapper.hexagon.domain.area;

import ruinMapper.hexagon.domain.ComponentSuper;
import ruinMapper.hexagon.domain.DomainInjector;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.RoomPort;

import java.awt.*;
import java.util.*;

public class Area extends ComponentSuper implements
        AreaPort {
    private String title;
    private String notes;
    private Map<Point, RoomPort> areaMap;

    private ContextPort contextPort;

    private CRUDRepositoryPort<Area> areaRepository;
    private UUID areaID;

    public Area(String title,
                ContextPort contextPort,
                CRUDRepositoryPort<Area> areaRepository) {
        this.title = title;
        this.notes = "";
        this.areaMap = new HashMap<>();
        this.contextPort = contextPort;

        this.areaRepository = areaRepository;
        this.areaID = UUID.randomUUID();

        createRoom(0, 0);
    }


    @Override
    public RoomPort createRoom(int x, int y) {
        RoomPort newRoom = DomainInjector
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


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Map<Point, RoomPort> getAreaMap() {
        return areaMap;
    }

    public void setAreaMap(
            Map<Point, RoomPort> areaMap) {
        this.areaMap = areaMap;
    }

    public ContextPort getContextPort() {
        return contextPort;
    }

    public void setContextPort(
            ContextPort contextPort) {
        this.contextPort = contextPort;
    }

    public UUID getAreaID() {
        return areaID;
    }

    public void setAreaID(UUID areaID) {
        this.areaID = areaID;
    }

}
