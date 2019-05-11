package ruinMapper.hexagon.infrastructure.persistence.hint;

import ruinMapper.hexagon.domain.hint.HintStatus;

public class HintDto {
    private String content;
    private String notes;
    private HintStatus status;
    private String roomID;
    private String hintID;

    public String getHintID() {
        return hintID;
    }

    public void setHintID(String hintID) {
        this.hintID = hintID;
    }

    public HintStatus getStatus() {
        return status;
    }

    public void setStatus(
            HintStatus status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }
}
