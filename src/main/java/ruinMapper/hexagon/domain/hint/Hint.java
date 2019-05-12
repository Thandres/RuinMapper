package ruinMapper.hexagon.domain.hint;

import ruinMapper.hexagon.domain.ComponentSuper;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.RoomPort;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Hint extends ComponentSuper implements
        HintPort {

    private String content;
    private String notes;
    private RoomPort room;
    private Set<TagPort> keywords;
    private HintStatus status;
    private ContextPort context;
    private CRUDRepositoryPort<Hint> hintRepository;
    private UUID hintID;


    public Hint(String content, RoomPort room,
                ContextPort context,
                CRUDRepositoryPort<Hint> hintRepository,
                UUID hintID) {
        // Injected
        this.content = content;
        this.context = context;
        this.room = room;
        this.hintRepository = hintRepository;
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
        return room;
    }

    @Override
    public void addKeyWord(TagPort keyword) {
        if (context.accessEveryKeyWord()
                .contains(keyword)) {
            keywords.add(keyword);
            saveState();
        }
    }

    @Override
    public void removeKeyWord(TagPort keywordToRemove) {
        if (keywords.remove(keywordToRemove)) {
            saveState();
        }
    }

    @Override
    public Set<TagPort> accessKeyWords() {
        return new HashSet<>(keywords);
    }

    @Override
    public void deleteHint() {
        if (room != null) {
            RoomPort roomToDelete = room;
            room = null;
            roomToDelete.deleteHint(this);
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

    public RoomPort getRoom() {
        return room;
    }

    public void setRoom(RoomPort room) {
        this.room = room;
    }

    public void setStatus(
            HintStatus status) {
        this.status = status;
    }

    public Set<TagPort> getKeywords() {
        return keywords;
    }

    public void setKeywords(
            Set<TagPort> keywords) {
        this.keywords = keywords;
    }

    public ContextPort getContext() {
        return context;
    }

    public void setContext(
            ContextPort context) {
        this.context = context;
    }
}
