package ruinMapper.hexagon.domain.room;

import ruinMapper.hexagon.ComponentFactory;
import ruinMapper.hexagon.domain.hint.Hint;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.quest.Quest;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Room implements RoomPort {
    private String title;
    private String notes;
    private Set<HintPort> hintSet;
    private Set<QuestPort> questSet;
    private Set<TagPort> tagSet;
    private CRUDRepositoryPort<Room> roomRepository;
    private UUID roomID;


    public Room(String title, String notes,
                Set<HintPort> hintSet,
                Set<QuestPort> questSet,
                Set<TagPort> tagSet,
                CRUDRepositoryPort<Room> roomRepository,
                UUID roomID) {
        this.title = title;
        this.notes = notes;
        this.hintSet = hintSet;
        this.questSet = questSet;
        this.tagSet = tagSet;

        this.roomRepository = roomRepository;
        this.roomID = roomID;
    }


    @Override
    public void changeTitle(String newTitle) {
        if (!title.equals(newTitle)) {
            title = newTitle;
            saveState();
        }
    }

    @Override
    public String accessTitle() {
        return title;
    }

    @Override
    public void changeNotes(String newNotes) {
        if (!notes.equals(newNotes)) {
            notes = newNotes;
            saveState();
        }
    }

    @Override
    public String accessNotes() {
        return notes;
    }


    @Override
    public HintPort createHint(String content) {
        Hint newHint = ComponentFactory
                .createHint(content, this);
        hintSet.add(newHint);
        saveState();
        return newHint;
    }

    @Override
    public void addHint(HintPort hint) {
        if (hintSet.add(hint)) {
            saveState();
        }
    }

    @Override
    public void removeHint(HintPort hint) {
        if (hintSet.remove(hint)) {
            saveState();
        }
    }

    @Override
    public Set<HintPort> accessHints() {
        return new HashSet<>(hintSet);
    }

    @Override
    public QuestPort createQuest(String title) {
        Quest newQuest = ComponentFactory
                .createQuest(title);
        questSet.add(newQuest);
        newQuest.addRoom(this);
        saveState();
        return newQuest;
    }

    @Override
    public void addQuest(QuestPort quest) {
        if (questSet.add(quest)) {
            quest.addRoom(this);
            saveState();
        }
    }

    @Override
    public void removeQuest(QuestPort quest) {
        if (questSet.remove(quest)) {
            quest.removeRoom(this);
            saveState();
        }
    }

    @Override
    public Set<QuestPort> accessQuests() {
        return new HashSet<>(questSet);
    }

    @Override
    public void addTag(TagPort port) {
        if (tagSet.add(port)) {
            port.addTaggable(this);
            saveState();
        }
    }

    @Override
    public void removeTag(TagPort port) {
        if (tagSet.remove(port)) {
            port.removeTaggable(this);
            saveState();
        }
    }

    @Override
    public Set<TagPort> accessTags() {
        return new HashSet<>(tagSet);
    }


    private void saveState() {
        roomRepository.update(this);
    }
}
