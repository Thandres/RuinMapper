package ruinMapper.hexagon.domain.area;

import ruinMapper.hexagon.domain.ComponentSuper;
import ruinMapper.hexagon.domain.DomainInjector;
import ruinMapper.hexagon.domain.context.Context;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.Room;
import ruinMapper.hexagon.domain.room.RoomPort;

import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

public class Area extends ComponentSuper implements
        AreaPort {
    private String title;
    private String notes;
    private Map<Point, String> areaMap;

    private String contextID;

    private CRUDRepositoryPort<Area> areaRepository;
    private CRUDRepositoryPort<Room> roomRepository;
    private CRUDRepositoryPort<Context> contextRepository;
    private UUID areaID;

    public Area(String title,
                String contextID,
                CRUDRepositoryPort<Area> areaRepository,
                CRUDRepositoryPort<Room> roomRepository,
                CRUDRepositoryPort<Context> contextRepository,
                UUID areaID) {
        this.roomRepository = roomRepository;
        this.areaRepository = areaRepository;
        this.title = title;
        this.contextRepository = contextRepository;
        this.areaID = areaID;
        this.contextID = contextID;
        this.notes = "";
        this.areaMap = new HashMap<>();
    }


    @Override
    public RoomPort createRoom(int x, int y) {
        RoomPort newRoom = DomainInjector
                .createRoom(new Point(x, y), this);
        areaMap.put(newRoom.accessCoordinates(),
                newRoom.toString());
        saveState();
        return newRoom;
    }

    @Override
    public RoomPort accessRoom(int x, int y) {
        Point pointToRetrieve = new Point(x, y);
        String roomID = areaMap.get(pointToRetrieve);
        return roomRepository.read(roomID);
    }

    @Override
    public Set<RoomPort> accessRooms() {
        return areaMap.values().stream()
                .map(roomID -> roomRepository.read(roomID))
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteRoom(int x, int y) {
        Point point = new Point(x, y);
        if (areaMap.containsKey(point)) {
            String roomToDelete = areaMap.remove(point);
            roomRepository.read(roomToDelete).deleteRoom();
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
        areaMap.values().stream()
                .map(roomID -> roomRepository.read(roomID))
                .forEach(room -> hintSet
                        .addAll(room.accessHints()));
        return hintSet;
    }

    @Override
    public void deleteArea() {
        if (contextID != null) {
            ContextPort temp = contextRepository
                    .read(contextID);
            contextID = null;
            Set<String> entries = new HashSet<>(
                    areaMap.values());
            for (String roomID : entries) {
                Point coordinates = roomRepository
                        .read(roomID)
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

    public Map<Point, String> getAreaMap() {
        return areaMap;
    }

    public void setAreaMap(
            Map<Point, String> areaMap) {
        this.areaMap = areaMap;
    }

    public String getContextID() {
        return contextID;
    }

    public void setContextID(
            String contextID) {
        this.contextID = contextID;
    }

    public UUID getAreaID() {
        return areaID;
    }

    public void setAreaID(UUID areaID) {
        this.areaID = areaID;
    }

}
