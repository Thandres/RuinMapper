package ruinMapper.hexagon.domain.quest;

import ruinMapper.hexagon.domain.room.RoomPort;

import java.util.Set;


/**
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

    public void addRoom(RoomPort room);

    public void removeRoom(RoomPort room);

    public void deleteQuest();
}
