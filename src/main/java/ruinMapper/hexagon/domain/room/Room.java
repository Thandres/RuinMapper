package ruinMapper.hexagon.domain.room;

import ruinMapper.hexagon.ComponentFactory;
import ruinMapper.hexagon.domain.QuestManager;
import ruinMapper.hexagon.domain.Questable;
import ruinMapper.hexagon.domain.TagManager;
import ruinMapper.hexagon.domain.Taggable;
import ruinMapper.hexagon.domain.hint.Hint;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.quest.Quest;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Room implements RoomPort, Taggable,
        Questable {
    private String title;
    private String notes;
    private Set<HintPort> hintSet;
    private QuestManager questManager;
    private TagManager tagManager;
    private CRUDRepositoryPort<Room> roomRepository;
    private UUID roomID;


    public Room(String title, String notes,
                Set<HintPort> hintSet,
                QuestManager questManager,
                TagManager tagManager,
                CRUDRepositoryPort<Room> roomRepository,
                UUID roomID) {
        this.title = title;
        this.notes = notes;
        this.hintSet = hintSet;
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

    private void saveState() {
        roomRepository.update(this);
    }

    @Override
    public boolean isContext() {
        return false;
    }
}
