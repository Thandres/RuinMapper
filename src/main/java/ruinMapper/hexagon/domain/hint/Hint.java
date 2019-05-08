package ruinMapper.hexagon.domain.hint;

import ruinMapper.hexagon.domain.invariant.RoomManager;
import ruinMapper.hexagon.domain.model.ComponentSuper;
import ruinMapper.hexagon.domain.model.ComponentType;
import ruinMapper.hexagon.domain.model.HasRoom;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.RoomPort;

import java.util.UUID;

public class Hint extends ComponentSuper implements
        HintPort, HasRoom {

    private String content;
    private String notes;
    private RoomManager roomManager;
    private HintStatus status;
    private CRUDRepositoryPort<Hint> hintRepository;
    private UUID hintID;


    public Hint(String content, String notes,
                RoomManager roomManager,
                HintStatus status,
                CRUDRepositoryPort<Hint> hintRepository,
                UUID hintID) {

        this.content = content;
        this.notes = notes;
        this.roomManager = roomManager;
        this.status = status;
        this.hintRepository = hintRepository;
        this.hintID = hintID;
    }

    @Override
    public String accessContent() {
        return content;
    }

    @Override
    public void changeContent(String text) {
        content = text;
        saveState();
    }

    @Override
    public String accessNotes() {
        return notes;
    }

    @Override
    public void changeNotes(String notes) {
        this.notes = notes;
        saveState();
    }

    @Override
    public RoomPort accessRoom() {
        RoomPort assignedRoom = null;// For testability initialize with null
        for (RoomPort room : roomManager
                .accessRooms(this)) {
            assignedRoom = room;
            break;
        }
        return assignedRoom;
    }

    @Override
    public void deleteHint() {
        roomManager.deleteManagedObject(this);
        hintRepository.delete(hintID.toString());
    }

    @Override
    public HintStatus getHintStatus() {
        return status;
    }

    @Override
    public void setHintStatus(HintStatus newStatus) {
        this.status = newStatus;
        saveState();
    }

    @Override
    public void saveState() {
        hintRepository.update(this);
    }

    @Override
    public ComponentType getType() {
        return ComponentType.HINT;
    }

    @Override
    public String toString() {
        return hintID.toString();
    }
}
