package ruinMapper.hexagon.infrastructure.persistence;

import java.awt.*;

public class RoomDto {
    private String title;
    private String notes;
    private Point cooridnates;
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
}
