package ruinMapper.hexagon.domain.room;

import ruinMapper.hexagon.domain.ComponentFactory;
import ruinMapper.hexagon.domain.ComponentSuper;
import ruinMapper.hexagon.domain.area.AreaPort;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class
Room extends ComponentSuper implements
        RoomPort {
    private String title;
    private String notes;
    private Point coordinates;
    private Set<HintPort> hints;
    private Set<QuestPort> quests;
    private Set<TagPort> tags;
    private ContextPort context;
    private AreaPort area;
    private CRUDRepositoryPort<Room> roomRepository;
    private UUID roomID;


    public Room(Point coordinates,
                ContextPort context,
                AreaPort area,
                CRUDRepositoryPort<Room> roomRepository) {
        this.context = context;
        this.area = area;
        this.title = "";
        this.notes = "";
        this.coordinates = coordinates;

        quests = new HashSet<>();
        tags = new HashSet<>();
        this.roomRepository = roomRepository;
        this.roomID = UUID.randomUUID();

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
    public HintPort createHint(String content) {
        HintPort newHint = ComponentFactory
                .createHint(content, this);
        hints.add(newHint);
        saveState();
        return newHint;
    }

    @Override
    public void deleteHint(HintPort hintToDelete) {
        if (hints.remove(hintToDelete)) {
            hintToDelete.deleteHint();
            saveState();
        }
    }

    @Override
    public Set<HintPort> accessHints() {
        return new HashSet<>(hints);
    }

    @Override
    public QuestPort createQuest(String title) {
        QuestPort newQuest = context
                .createQuest(title);
        quests.add(newQuest);
        newQuest.addQuestRoom(this);

        saveState();
        return newQuest;
    }

    @Override
    public void addQuest(QuestPort questToAdd) {
        if (quests.add(questToAdd)) {
            questToAdd.addQuestRoom(this);
            saveState();
        }

    }

    @Override
    public void removeQuest(QuestPort questToRemove) {
        if (quests.remove(questToRemove)) {
            questToRemove.removeQuestRoom(this);
            saveState();
        }
    }

    @Override
    public Set<QuestPort> accessQuests() {
        return new HashSet<>(quests);
    }

    @Override
    public void tagRoom(TagPort validTag) {
        if (context.accessValidTags().contains(validTag)) {
            tags.add(validTag);
            saveState();
        }
    }

    @Override
    public void removeTag(TagPort tagToRemove) {
        if (tags.remove(tagToRemove)) {
            saveState();
        }
    }

    @Override
    public Set<TagPort> accessTags() {
        return new HashSet<>(tags);
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
        if (area != null) {
            Set<HintPort> temp = new HashSet<>(hints);
            hints.clear();
            temp.forEach(HintPort::deleteHint);

            Set<QuestPort> tempQ = new HashSet<>(quests);
            quests.clear();
            tempQ.forEach(questPort -> questPort
                    .removeQuestRoom(this));

            tags.clear();

            AreaPort tempC = area;
            area = null;
            tempC.deleteRoom(coordinates.x, coordinates.y);

            context = null;
            roomRepository.delete(toString());
        }
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

    public Set<HintPort> getHints() {
        return hints;
    }

    public void setHints(
            Set<HintPort> hints) {
        this.hints = hints;
    }

    public Set<QuestPort> getQuests() {
        return quests;
    }

    public void setQuests(
            Set<QuestPort> quests) {
        this.quests = quests;
    }

    public Set<TagPort> getTags() {
        return tags;
    }

    public void setTags(
            Set<TagPort> tags) {
        this.tags = tags;
    }

    public ContextPort getContext() {
        return context;
    }

    public void setContext(
            ContextPort context) {
        this.context = context;
    }

    public AreaPort getArea() {
        return area;
    }

    public void setArea(AreaPort area) {
        this.area = area;
    }

    public UUID getRoomID() {
        return roomID;
    }

    public void setRoomID(UUID roomID) {
        this.roomID = roomID;
    }
}
