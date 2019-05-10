package ruinMapper.hexagon.domain.quest;

import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.model.ComponentSuper;
import ruinMapper.hexagon.domain.model.ComponentType;
import ruinMapper.hexagon.domain.model.HasRoom;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.RoomPort;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Quest extends ComponentSuper implements
        QuestPort, HasRoom {

    private String title;
    private String description;
    private String notes;
    private Set<RoomPort> rooms;
    private ContextPort context;
    private QuestStatus status;
    private CRUDRepositoryPort<Quest> questRepository;
    private UUID questID;


    public Quest(String title,
                 ContextPort context,
                 CRUDRepositoryPort<Quest> questRepository) {
        this.title = title;
        this.context = context;
        this.description = "";
        this.notes = "";
        this.status = QuestStatus.ACTIVE;
        rooms = new HashSet<>();
        this.questRepository = questRepository;
        this.questID = UUID.randomUUID();
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
        return new HashSet<>(rooms);
    }

    @Override
    public void addQuestRoom(RoomPort roomToAdd) {
        if (rooms.add(roomToAdd)) {
            roomToAdd.addQuest(this);
            saveState();
        }
    }

    @Override
    public void removeQuestRoom(RoomPort roomToRemove) {
        if (rooms.remove(roomToRemove)) {
            roomToRemove.removeQuest(this);
            saveState();
        }
    }

    @Override
    public void deleteQuest() {
        if (context != null) {
            Set<RoomPort> temp = new HashSet<>(rooms);
            rooms.clear();
            temp.forEach(
                    roomPort -> roomPort.removeQuest(this));
            ContextPort tempC = context;
            context = null;
            tempC.deleteQuest(this);
            questRepository.delete(questID.toString());
        }
    }

    @Override
    public void saveState() {
        questRepository.update(this);
    }


    @Override
    public ComponentType getType() {
        return ComponentType.QUEST;
    }

    @Override
    public String toString() {
        return questID.toString();
    }
}
