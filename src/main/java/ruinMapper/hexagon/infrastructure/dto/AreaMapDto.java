package ruinMapper.hexagon.infrastructure.dto;

import java.awt.*;
import java.util.Map;

public class AreaMapDto {
    private String title;
    private Map<Point, RoomDto> roomMap;
    private String contextID;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<Point, RoomDto> getRoomMap() {
        return roomMap;
    }

    public void setRoomMap(
            Map<Point, RoomDto> roomMap) {
        this.roomMap = roomMap;
    }

    public String getContextID() {
        return contextID;
    }

    public void setContextID(String contextID) {
        this.contextID = contextID;
    }
}
