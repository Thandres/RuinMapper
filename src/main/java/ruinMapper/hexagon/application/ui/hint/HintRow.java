package ruinMapper.hexagon.application.ui.hint;

import javafx.scene.control.TextArea;
import ruinMapper.hexagon.domain.hint.HintStatus;

public class HintRow {
    private TextArea content;
    private TextArea notes;
    private String room;
    private KeywordList keywords;
    private String status;

    public HintRow(TextArea content,
                   TextArea notes, String room,
                   KeywordList keywords,
                   HintStatus status) {
        this.content = content;
        this.notes = notes;
        this.room = room;
        this.keywords = keywords;
        this.status = status.toString();
    }

    public TextArea getContent() {
        return content;
    }

    public void setContent(TextArea content) {
        this.content = content;
    }

    public TextArea getNotes() {
        return notes;
    }

    public void setNotes(TextArea notes) {
        this.notes = notes;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public KeywordList getKeywords() {
        return keywords;
    }

    public void setKeywords(
            KeywordList keywords) {
        this.keywords = keywords;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(
            String status) {
        this.status = status;
    }
}
