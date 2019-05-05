package ruinMapper.hexagon.domain.room;

import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.invariant.HintManager;
import ruinMapper.hexagon.domain.invariant.QuestManager;
import ruinMapper.hexagon.domain.invariant.TagManager;
import ruinMapper.hexagon.domain.model.*;
import ruinMapper.hexagon.domain.quest.Quest;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class
Room extends ComponentSuper implements
        RoomPort, HasQuest, HasTag,
        HasHint {
    private String title;
    private String notes;
    private HintManager hintManager;
    private QuestManager questManager;
    private TagManager tagManager;
    private CRUDRepositoryPort<Room> roomRepository;
    private UUID roomID;


    public Room(String title, String notes,
                HintManager hintManager,
                QuestManager questManager,
                TagManager tagManager,
                CRUDRepositoryPort<Room> roomRepository,
                UUID roomID) {
        this.title = title;
        this.notes = notes;
        this.hintManager = hintManager;

        this.questManager = questManager;
        this.tagManager = tagManager;
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
        HintPort newHint = ComponentFactory
                .createHint(content, this);
        hintManager.addHint(newHint, this);
        saveState();
        return newHint;
    }

    @Override
    public void addHint(HintPort hint) {
        hintManager.addHint(hint, this);
        saveState();
    }

    @Override
    public void removeHint(HintPort hint) {
        hintManager.removeHint(hint, this);
        saveState();
    }

    @Override
    public Set<HintPort> accessHints() {
        return new HashSet<>(hintManager.accessHints(this));
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
    public void addTag(TagPort tagPort) {
        tagManager.addTag(tagPort, this);
        saveState();
    }

    @Override
    public void removeTag(TagPort tagPort) {
        tagManager.removeTag(tagPort, this);
        saveState();
    }

    @Override
    public Set<TagPort> accessTags() {
        return new HashSet<>(tagManager.accessTags(this));
    }

    @Override
    public void saveState() {
        roomRepository.update(this);
    }


    @Override
    public ComponentType getType() {
        return ComponentType.ROOM;
    }

    @Override
    public String toString() {
        return roomID.toString();
    }
}
