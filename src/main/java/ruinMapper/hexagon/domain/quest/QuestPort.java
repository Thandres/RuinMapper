package ruinMapper.hexagon.domain.quest;

import ruinMapper.hexagon.domain.room.RoomPort;

import java.util.Set;


/**
 * The interface for use by the application layer. Every outside concern
 * of a quest comes through the questPort
 * <p>
 * Lets you keep track of quests that arise and the rooms
 * relevant to that quest. Also keeps track of the queststatus
 * and lets you take additional notes
 */
public interface QuestPort {
    /**
     * Changes the title of the Quest to the specified title.
     *
     * @param newTitle The new title
     */
    public void changeTitle(String newTitle);

    /**
     * Returns the current title of the Quest
     *
     * @return Title of the Quest
     */
    public String accessTitle();

    /**
     * Changes the description of the Quest to the specified description
     *
     * @param newDescription The new description
     */
    public void changeDescription(String newDescription);

    /**
     * Returns the current description of the Quest
     *
     * @return Description of the Quest
     */
    public String accessDescription();

    /**
     * Changes the notes of the Quest to the specified notes
     *
     * @param newNotes The new notes
     */
    public void changeNotes(String newNotes);

    /**
     * Returns the current notes of the Quest
     *
     * @return Notes of the Quest
     */
    public String accessNotes();

    /**
     * Returns the current QuestStatus.
     *
     * @return Status of Quest
     */
    public QuestStatus accessQuestStatus();

    /**
     * Changes the status of the Quest to the specified status.
     *
     * @param status The new Status
     */
    public void changeQuestStatus(QuestStatus status);

    /**
     * Returns all Rooms associated with this Quest
     *
     * @return All rooms for this Quest
     */
    public Set<RoomPort> accessQuestRooms();


    /**
     * Adds the Room to the Collection of Rooms.
     * <p>
     * The Room also adds this Quest to the collection of Quests
     *
     * @param roomToAdd Room to add to the collection of Rooms
     */
    public void addQuestRoom(RoomPort roomToAdd);

    /**
     * Removes the Room from the collection of Rooms.
     * <p>
     * The Room also removes the Quest from its collection of Quests.
     *
     * @param roomToRemove The Room to be removed from the collection of Rooms
     */
    public void removeQuestRoom(RoomPort roomToRemove);

    // Deleting the Quest completely removes all references to the Quest from everywhere

    /**
     * Deletes this Quest from the context.
     * The Quest will also be removed from all assigned Rooms.
     */
    public void deleteQuest();
}
