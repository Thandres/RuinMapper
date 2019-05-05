package ruinMapper.hexagon.domain.hint;

import ruinMapper.hexagon.domain.marker.ComponentType;
import ruinMapper.hexagon.domain.marker.HasRoom;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.RoomPort;

import java.util.UUID;

public class Hint implements HintPort, HasRoom {

    private String content;
    private String notes;
    private RoomPort room;
    private HintStatus status;
    private CRUDRepositoryPort<Hint> hintRepository;
    private UUID hintID;


    public Hint(String content, String notes,
                RoomPort room,
                HintStatus status,
                CRUDRepositoryPort<Hint> hintRepository,
                UUID hintID) {

        this.content = content;
        this.notes = notes;
        this.room = room;
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
        return room;
    }

    @Override
    public void removeRoom() {
        if (room != null) {
            room.removeHint(this);
            room = null;
            saveState();
        }
    }

    @Override
    public void addRoom(RoomPort room) {
        //TODO in all methods that have this pattern:
        // TODO Validation logik ausbauen
        if (this.room != null) {
            this.room = room;
            room.addHint(this);
            saveState();
        }
    }

    @Override
    public void deleteHint() {
        room.removeHint(this);
        hintRepository.delete(hintID.toString());
    }

    @Override
    public HintStatus getHintStatus() {
        return status;
    }

    @Override
    public void setHintStatus(HintStatus status) {
        this.status = status;
        saveState();
    }

    private void saveState() {
        hintRepository.update(this);
    }

    @Override
    public ComponentType getType() {
        return ComponentType.HINT;
    }
}
