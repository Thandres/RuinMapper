package ruinMapper.hexagon.infrastructure.persistence.room;

import java.util.Set;

public class RoomDto {
    private String title;
    private String notes;

    private int x;
    private int y;
    private Set<String> hints;
    private Set<String> quests;
    private Set<String> tags;
    private String contextID;

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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

}
