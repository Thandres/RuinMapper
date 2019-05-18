package ruinMapper.hexagon.domain.room;

import ruinMapper.hexagon.domain.ComponentSuper;
import ruinMapper.hexagon.domain.DomainInjector;
import ruinMapper.hexagon.domain.context.Context;
import ruinMapper.hexagon.domain.hint.Hint;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.quest.Quest;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.tag.Tag;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class
Room extends ComponentSuper implements
        RoomPort {
    private String title;
    private String notes;
    private Point coordinates;
    private Set<String> hints;
    private Set<String> quests;
    private Set<String> tags;
    private String contextID;

    private CRUDRepositoryPort<Room> roomRepository;
    private CRUDRepositoryPort<Hint> hintRepository;
    private CRUDRepositoryPort<Quest> questRepository;
    private CRUDRepositoryPort<Tag> tagRepository;
    private CRUDRepositoryPort<Context> contextRepository;
    private UUID roomID;


    public Room(Point coordinates,
                String contextID,

                CRUDRepositoryPort<Room> roomRepository,
                CRUDRepositoryPort<Hint> hintRepository,
                CRUDRepositoryPort<Quest> questRepository,
                CRUDRepositoryPort<Tag> tagRepository,
                CRUDRepositoryPort<Context> contextRepository,
                UUID roomID) {
        this.contextID = contextID;
        this.hintRepository = hintRepository;
        this.questRepository = questRepository;
        this.tagRepository = tagRepository;
        this.roomRepository = roomRepository;
        this.contextRepository = contextRepository;

        this.title = "";
        this.notes = "";
        this.coordinates = coordinates;

        quests = new HashSet<>();
        tags = new HashSet<>();
        this.roomID = roomID;

        hints = new HashSet<>();
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
    public AreaPort accessArea() {
        return area;
    }


    @Override
    public HintPort createHint(String content) {
        HintPort newHint = DomainInjector
                .createHint(content, this);
        hints.add(newHint.toString());
        saveState();
        return newHint;
    }

    @Override
    public void deleteHint(HintPort hintToDelete) {
        if (hints.remove(hintToDelete.toString())) {
            hintToDelete.deleteHint();
            saveState();
        }
    }

    @Override
    public Set<HintPort> accessHints() {
        return hints.stream()
                .map(hintID -> hintRepository.read(hintID))
                .collect(Collectors.toSet());
    }

    @Override
    public QuestPort createQuest(String title) {
        QuestPort newQuest = contextRepository
                .read(contextID)
                .createQuest(title);
        quests.add(newQuest.toString());
        newQuest.addQuestRoom(this);

        saveState();
        return newQuest;
    }

    @Override
    public void addQuest(QuestPort questToAdd) {
        if (quests.add(questToAdd.toString())) {
            questToAdd.addQuestRoom(this);
            saveState();
        }

    }

    @Override
    public void removeQuest(QuestPort questToRemove) {
        if (quests.remove(questToRemove.toString())) {
            questToRemove.removeQuestRoom(this);
            saveState();
        }
    }

    @Override
    public Set<QuestPort> accessQuests() {
        return quests.stream()
                .map(questID -> questRepository
                        .read(questID))
                .collect(Collectors.toSet());
    }

    @Override
    public void tagRoom(TagPort validTag) {
        if (contextRepository.read(contextID)
                .accessValidTags().contains(validTag)) {
            tags.add(validTag.toString());
            saveState();
        }
    }

    @Override
    public void removeTag(TagPort tagToRemove) {
        if (tags.remove(tagToRemove.toString())) {
            saveState();
        }
    }

    @Override
    public Set<TagPort> accessTags() {
        return tags.stream()
                .map(tagID -> tagRepository.read(tagID))
                .collect(Collectors.toSet());
    }

    @Override
    public void saveState() {
        roomRepository.update(this);
    }

    @Override
    public String toString() {
        return roomID.toString();
    }

    @Override
    public void changeCoordinates(Point point) {
        coordinates = point;
    }

    @Override
    public void deleteRoom() {

        hints.forEach(
                hintID -> hintRepository.delete(hintID));
        hints.clear();

        Set<QuestPort> tempQ = accessQuests();
        quests.clear();
        tempQ.forEach(questPort -> questPort
                .removeQuestRoom(this));

        tags.clear();

        contextID = null;
        roomRepository.delete(toString());

    }

    @Override
    public Point accessCoordinates() {
        return coordinates;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public Set<String> getHints() {
        return hints;
    }

    public void setHints(
            Set<String> hints) {
        this.hints = hints;
    }

    public Set<String> getQuests() {
        return quests;
    }

    public void setQuests(
            Set<String> quests) {
        this.quests = quests;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(
            Set<String> tags) {
        this.tags = tags;
    }

    public String getContextID() {
        return contextID;
    }

    public void setContextID(
            String contextID) {
        this.contextID = contextID;
    }

    public UUID getRoomID() {
        return roomID;
    }

    public void setRoomID(UUID roomID) {
        this.roomID = roomID;
    }
}
