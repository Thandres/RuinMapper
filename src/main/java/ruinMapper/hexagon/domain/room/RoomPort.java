package ruinMapper.hexagon.domain.room;

import ruinMapper.hexagon.domain.area.AreaPort;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.awt.*;
import java.util.Set;

/**
 * Manages all aspects about a room. Allows for takning notes,
 * creating and adding quests to rooms, adding hints and marking
 * the room with custom marks.
 */
public interface RoomPort {
    /**
     * Changes the title of the Room to the specified title
     *
     * @param newTitle the new title
     */
    public void changeTitle(String newTitle);

    /**
     * Returns the current title of the Room
     *
     * @return The title of the Room
     */
    public String accessTitle();

    /**
     * Changes the notes of the Room to the specified notes.
     *
     * @param newNotes The new notes
     */
    public void changeNotes(String newNotes);

    /**
     * Returns the current notes of the Room
     *
     * @return The notes of the Room
     */
    public String accessNotes();

    /**
     * Returns the Area the Room is assigned to.
     *
     * @return The Rooms Area
     */
    public AreaPort accessArea();

    /**
     * Creates a Hint with the specified content for this Room,
     * returns the Hint for convenience.
     * <p>
     * Adds the Hint to the collection of Hints
     *
     * @param content Content of the Hint
     * @return The created Hint
     */
    public HintPort createHint(String content);

    /**
     * Deletes the Hint from the Room.
     * The removed Hint will also be deleted from Context.
     *
     * @param hintToDelete The Hint that should be deleted
     */
    public void deleteHint(HintPort hintToDelete);

    /**
     * Returns all Hints assigned to the Room
     *
     * @return The Rooms Hints
     */
    public Set<HintPort> accessHints();

    /**
     * Creates a Quest with the specified title and the
     * current Room as an assigned Room, returns the Quest for convenience.
     *
     * @param title Title of the Quest
     * @return The created Quest
     */
    public QuestPort createQuest(String title);

    /**
     * Assigns the Quest to this Room.
     * The Quest also assigns this Room to its assigned Rooms.
     *
     * @param questToAdd The Quest to add to the Room
     */
    public void addQuest(QuestPort questToAdd);

    /**
     * Removes the Quest from this Room.
     * The Quest also removes this Room from its assigned Rooms
     *
     * @param questToRemove The Quest to remove from this Room.
     */
    public void removeQuest(QuestPort questToRemove);

    /**
     * Returns all Quests currently assigned to the Room.
     *
     * @return All Quests of this Room
     */
    public Set<QuestPort> accessQuests();

    /**
     * Tags the Room with the validTag.
     * The Tag has to be a valid Tag or it wont be added.
     *
     * @param validTag Tag that is valid in the current Context
     */
    public void tagRoom(TagPort validTag);

    /**
     * Removes the Tag from the Room.
     *
     * @param tagToRemove
     */
    public void removeTag(TagPort tagToRemove);

    /**
     * Returns all Tags the Room has been tagged with.
     *
     * @return
     */
    public Set<TagPort> accessTags();

    /**
     * Returns the current coordinates of the Room in its assigned Area.
     *
     * @return Coordinates in form of a Point
     */
    public Point accessCoordinates();

    /**
     * Changes the coordinates of the Room to the specified Point
     *
     * @param newCoordinates The new coordinates
     */
    public void changeCoordinates(Point newCoordinates);

    /**
     * Deletes the Room from Context.
     * All Hints assigned to the Room are deleted as well.
     * All Quests assigned to this room remove the Room from themselves.
     * The Area of the Room removes the Room from its assigned rooms
     */
    public void deleteRoom();

}
