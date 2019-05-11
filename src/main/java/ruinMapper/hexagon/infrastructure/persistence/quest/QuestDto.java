package ruinMapper.hexagon.infrastructure.persistence.quest;

import ruinMapper.hexagon.domain.quest.QuestStatus;

import java.util.Set;

public class QuestDto {
    private String title;
    private String description;
    private String notes;


    private Set<String> rooms;
    private String contextID;
    private QuestStatus status;
    private String questID;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public QuestStatus getStatus() {
        return status;
    }

    public void setStatus(
            QuestStatus status) {
        this.status = status;
    }

    public String getQuestID() {
        return questID;
    }

    public void setQuestID(String questID) {
        this.questID = questID;
    }

    public Set<String> getRooms() {
        return rooms;
    }

    public void setRooms(Set<String> rooms) {
        this.rooms = rooms;
    }

    public String getContextID() {
        return contextID;
    }

    public void setContextID(String contextID) {
        this.contextID = contextID;
    }
}
