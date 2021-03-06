package ruinMapper.hexagon.infrastructure.persistence.area;

import java.util.Map;

public class AreaDto {
    private String title;
    private String notes;
    private Map<String, String> roomMap;
    private String contextID;
    private String areaID;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAreaID() {
        return areaID;
    }

    public void setAreaID(String areaID) {
        this.areaID = areaID;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Map<String, String> getRoomMap() {
        return roomMap;
    }

    public void setRoomMap(
            Map<String, String> roomMap) {
        this.roomMap = roomMap;
    }

    public String getContextID() {
        return contextID;
    }

    public void setContextID(String contextID) {
        this.contextID = contextID;
    }
}
