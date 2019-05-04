package ruinMapper.hexagon.domain.quest;

import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.RoomPort;

import java.util.Set;
import java.util.UUID;

public class Quest implements QuestPort {

    private String title;
    private String description;
    private String notes;
    private RoomManager roomManager;
    private QuestStatus status;
    private CRUDRepositoryPort<Quest> questRepository;
    private UUID questID;


    public Quest(String title, String description,
                 String notes,
                 RoomManager roomManager,
                 QuestStatus status,
                 CRUDRepositoryPort<Quest> questRepository,
                 UUID questID) {
        this.title = title;
        this.description = description;
        this.notes = notes;
        this.roomManager = roomManager;
        this.status = status;

        this.questRepository = questRepository;
        this.questID = questID;
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
        return roomManager.accessRooms(this);
    }

    @Override
    public void addRoom(RoomPort room) {
        roomManager.addRoom(room, this);
        saveState();
    }

    @Override
    public void removeRoom(RoomPort room) {
        roomManager.removeRoom(room, this);
        saveState();
    }

    @Override
    public void deleteQuest() {
        roomManager.accessRooms(this).forEach(
                roomPort -> roomManager
                        .removeRoom(roomPort, this));
        questRepository.delete(questID.toString());
    }

    private void saveState() {
        questRepository.update(this);
    }
}
