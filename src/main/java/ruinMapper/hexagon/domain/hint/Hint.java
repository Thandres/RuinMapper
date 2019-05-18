package ruinMapper.hexagon.domain.hint;

import ruinMapper.hexagon.domain.ComponentSuper;
import ruinMapper.hexagon.domain.context.Context;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.Room;
import ruinMapper.hexagon.domain.room.RoomPort;
import ruinMapper.hexagon.domain.tag.Tag;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Hint extends ComponentSuper implements
        HintPort {

    private String content;
    private String notes;
    private String roomID;
    private Set<String> keywords;
    private HintStatus status;
    private String contextID;
    private CRUDRepositoryPort<Hint> hintRepository;
    private CRUDRepositoryPort<Context> contextRepository;
    private CRUDRepositoryPort<Room> roomRepository;
    private CRUDRepositoryPort<Tag> tagRepository;
    private UUID hintID;

    public Hint(String content, String roomID,
                String contextID,
                CRUDRepositoryPort<Hint> hintRepository,
                CRUDRepositoryPort<Context> contextRepository,
                CRUDRepositoryPort<Room> roomRepository,
                CRUDRepositoryPort<Tag> tagRepository,
                UUID hintID) {
        // Injected
        this.content = content;
        this.contextID = contextID;
        this.roomID = roomID;
        this.hintRepository = hintRepository;
        this.contextRepository = contextRepository;
        this.roomRepository = roomRepository;
        this.tagRepository = tagRepository;
        this.hintID = hintID;
        // Self
        this.notes = "";
        this.keywords = new HashSet<>();
        this.status = HintStatus.NO_IDEA;
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
        return roomRepository.read(roomID);
    }

    @Override
    public void addKeyWord(TagPort keyword) {
        if (contextRepository.read(contextID)
                .accessEveryKeyWord()
                .contains(keyword)) {
            keywords.add(keyword.toString());
            saveState();
        }
    }

    @Override
    public void removeKeyWord(TagPort keywordToRemove) {
        if (keywords.remove(keywordToRemove.toString())) {
            saveState();
        }
    }

    @Override
    public Set<TagPort> accessKeyWords() {
        return keywords.stream()
                .map(keywordID -> tagRepository
                        .read(keywordID))
                .collect(Collectors.toSet());

    }

    @Override
    public void deleteHint() {
        if (roomID != null) {
            RoomPort room = roomRepository.read(roomID);
            roomID = null;
            room.deleteHint(this);
            keywords.clear();
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
    public String toString() {
        return hintID.toString();
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

    public void setStatus(
            HintStatus status) {
        this.status = status;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(
            Set<String> keywords) {
        this.keywords = keywords;
    }

    public String getContextID() {
        return contextID;
    }

    public void setContextID(
            String contextID) {
        this.contextID = contextID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public HintStatus getStatus() {
        return status;
    }

    public UUID getHintID() {
        return hintID;
    }

    public void setHintID(UUID hintID) {
        this.hintID = hintID;
    }
}
