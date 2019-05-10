package ruinMapper.hexagon.domain.hint;

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
    private RoomPort room;
    private HintStatus status;
    private CRUDRepositoryPort<Hint> hintRepository;
    private UUID hintID;


    public Hint(String content, RoomPort room,
                CRUDRepositoryPort<Hint> hintRepository) {

        this.content = content;
        this.notes = "";
        this.room = room;

        this.status = HintStatus.NO_IDEA;
        this.hintRepository = hintRepository;
        this.hintID = UUID.randomUUID();
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
        return room;
    }

    @Override
    public void deleteHint() {
        if (room != null) {
            RoomPort roomToDelete = room;
            room = null;
            roomToDelete.deleteHint(this);
            hintRepository.delete(hintID.toString());
        }

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

    public RoomPort getRoom() {
        return room;
    }

    public void setRoom(RoomPort room) {
        this.room = room;
    }

    public HintStatus getStatus() {
        return status;
    }

    public void setStatus(
            HintStatus status) {
        this.status = status;
    }

    public UUID getHintID() {
        return hintID;
    }

    public void setHintID(UUID hintID) {
        this.hintID = hintID;
    }
}
