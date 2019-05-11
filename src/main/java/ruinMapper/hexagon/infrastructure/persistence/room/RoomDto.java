package ruinMapper.hexagon.infrastructure.persistence.room;

import java.awt.*;
import java.util.Set;

public class RoomDto {
    private String title;
    private String notes;
    private Point cooridnates;
    private Set<String> hints;
    private Set<String> quests;
    private Set<String> tags;
    private String contextID;
    private String areaID;
    private String roomID;

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

    public Point getCooridnates() {
        return cooridnates;
    }

    public void setCooridnates(Point cooridnates) {
        this.cooridnates = cooridnates;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public Set<String> getHints() {
        return hints;
    }

    public void setHints(Set<String> hints) {
        this.hints = hints;
    }

    public Set<String> getQuests() {
        return quests;
    }

    public void setQuests(Set<String> quests) {
        this.quests = quests;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public String getContextID() {
        return contextID;
    }

    public void setContextID(String contextID) {
        this.contextID = contextID;
    }

    public String getAreaID() {
        return areaID;
    }

    public void setAreaID(String areaID) {
        this.areaID = areaID;
    }
}
