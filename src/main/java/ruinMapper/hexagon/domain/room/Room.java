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
    private QuestManager questManager;

    private Set<TagPort> tagSet;
    private CRUDRepositoryPort<Room> roomRepository;
    private UUID roomID;


    public Room(String title, String notes,
                Set<HintPort> hintSet,
                QuestManager questManager,
                Set<TagPort> tagSet,
                CRUDRepositoryPort<Room> roomRepository,
                UUID roomID) {
        this.title = title;
        this.notes = notes;
        this.hintSet = hintSet;
        this.questManager = questManager;

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
        questManager.addQuest(newQuest, this);
        newQuest.addRoom(this);
        saveState();
        return newQuest;
    }

    @Override
    public void addQuest(QuestPort quest) {
        questManager.addQuest(quest, this);

        saveState();

    }

    @Override
    public void removeQuest(QuestPort quest) {
        questManager.removeQuest(quest, this);

        saveState();

    }

    @Override
    public Set<QuestPort> accessQuests() {
        return questManager.accessQuests(this);
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
