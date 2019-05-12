package ruinMapper.hexagon.infrastructure.persistence.hint;

import ruinMapper.hexagon.domain.hint.HintStatus;

import java.util.Set;

public class HintDto {
    private String content;
    private String notes;
    private Set<String> keywords;
    private String contextID;
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

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }

    public String getContextID() {
        return contextID;
    }

    public void setContextID(String contextID) {
        this.contextID = contextID;
    }
}
