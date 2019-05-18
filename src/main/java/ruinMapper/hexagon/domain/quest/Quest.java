package ruinMapper.hexagon.domain.quest;

import ruinMapper.hexagon.domain.ComponentSuper;
import ruinMapper.hexagon.domain.context.Context;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.Room;
import ruinMapper.hexagon.domain.room.RoomPort;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Quest extends ComponentSuper implements
        QuestPort {

    private String title;
    private String description;
    private String notes;
    private Set<String> rooms;
    private String contextId;
    private QuestStatus status;
    private CRUDRepositoryPort<Quest> questRepository;
    private CRUDRepositoryPort<Room> roomRepository;
    private CRUDRepositoryPort<Context> contextRepository;
    private UUID questID;


    public Quest(String title,
                 String contextId,
                 CRUDRepositoryPort<Quest> questRepository,
                 CRUDRepositoryPort<Room> roomRepository,
                 CRUDRepositoryPort<Context> contextRepository,
                 UUID questID) {
        this.title = title;
        this.contextId = contextId;
        this.roomRepository = roomRepository;
        this.contextRepository = contextRepository;
        this.questID = questID;
        this.description = "";
        this.notes = "";
        this.status = QuestStatus.ACTIVE;
        rooms = new HashSet<>();
        this.questRepository = questRepository;

    }


    @Override
    public void changeTitle(String newTitle) {
        title = newTitle;
        saveState();
    }

    @Override
    public String accessTitle() {
        return title;
    }

    @Override
    public void changeDescription(String newDescription) {
        description = newDescription;
        saveState();
    }

    @Override
    public String accessDescription() {
        return description;
    }

    @Override
    public void changeNotes(String newNotes) {
        notes = newNotes;
        saveState();
    }

    @Override
    public String accessNotes() {
        return notes;
    }

    @Override
    public QuestStatus accessQuestStatus() {
        return status;
    }

    @Override
    public void changeQuestStatus(QuestStatus status) {
        this.status = status;
        saveState();
    }

    @Override
    public Set<RoomPort> accessQuestRooms() {
        return rooms.stream()
                .map(roomID -> roomRepository.read(roomID))
                .collect(Collectors.toSet());
    }

    @Override
    public void addQuestRoom(RoomPort roomToAdd) {
        if (rooms.add(roomToAdd.toString())) {
            roomToAdd.addQuest(this);
            saveState();
        }
    }

    @Override
    public void removeQuestRoom(RoomPort roomToRemove) {
        if (rooms.remove(roomToRemove.toString())) {
            roomToRemove.removeQuest(this);
            saveState();
        }
    }

    @Override
    public void deleteQuest() {
        if (contextId != null) {

            accessQuestRooms().forEach(
                    roomPort -> roomPort.removeQuest(this));
            String tempC = contextId;
            contextId = null;
            contextRepository.read(tempC).deleteQuest(this);
            questRepository.delete(questID.toString());
        }
    }

    @Override
    public void saveState() {
        questRepository.update(this);
    }

    @Override
    public String toString() {
        return questID.toString();
    }

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

    public Set<String> getRooms() {
        return rooms;
    }

    public void setRooms(
            Set<String> rooms) {
        this.rooms = rooms;
    }

    public String getContextId() {
        return contextId;
    }

    public void setContextId(
            String contextId) {
        this.contextId = contextId;
    }

    public QuestStatus getStatus() {
        return status;
    }

    public void setStatus(
            QuestStatus status) {
        this.status = status;
    }

    public UUID getQuestID() {
        return questID;
    }

    public void setQuestID(UUID questID) {
        this.questID = questID;
    }
}
