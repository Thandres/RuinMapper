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
    public void changeTitle(String newTitle);

    public String accessTitle();

    public void changeDescription(String newDescription);

    public String accessDescription();

    public void changeNotes(String newNotes);

    public String accessNotes();

    public QuestStatus accessQuestStatus();

    public void changeQuestStatus(QuestStatus status);

    public Set<RoomPort> accessQuestRooms();

    // Adding a room also updates the Room
    public void addRoom(RoomPort room);

    // Removing a room also updates the Room
    public void removeRoom(RoomPort room);

    // Deleting the Quest completely removes all references to the Quest from everywhere
    public void deleteQuest();
}
