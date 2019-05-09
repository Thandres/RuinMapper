package ruinMapper.hexagon.infrastructure.dto;

import ruinMapper.hexagon.domain.hint.HintStatus;

public class HintDto {
    private String content;
    private String notes;
    private HintStatus status;
    private String hintId;

    public String getHintId() {
        return hintId;
    }

    public void setHintId(String hintId) {
        this.hintId = hintId;
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
}
