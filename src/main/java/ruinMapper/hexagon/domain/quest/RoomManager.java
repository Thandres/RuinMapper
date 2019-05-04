package ruinMapper.hexagon.domain.quest;

import ruinMapper.hexagon.domain.room.RoomPort;

import java.util.Set;


/**
 * Interface for abstracting the Room functionality of QuestPort away
 */
public interface RoomManager {
    public void addRoom(RoomPort value, QuestPort key);

    public void removeRoom(RoomPort value, QuestPort key);

    public Set<RoomPort> accessRooms(QuestPort questPort);

    public void deleteQuest(QuestPort questPort);
}
