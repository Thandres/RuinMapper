package ruinMapper.hexagon.domain.quest;

import ruinMapper.hexagon.domain.room.RoomPort;

import java.util.Set;


/**
 * Interface that keeps the Invariants of Quests intact
 */
public interface RoomManager {

    /**
     * Invariants:
     * 1. If a Room is added to the Quest, add the Quest to the Room as well
     *
     * @param value Room to add
     * @param key   Quest that adds the room
     */
    public void addRoom(RoomPort value, QuestPort key);

    /**
     * Invariants:
     * 1. If a Room is removed from a Quest, remove the Quest from the Room as well
     * @param value Room to remove
     * @param key Quest that adds the room
     */
    public void removeRoom(RoomPort value, QuestPort key);

    /**
     * Returns the Rooms assigned to the Quest
     * @param questPort
     * @return
     */
    public Set<RoomPort> accessRooms(QuestPort questPort);

    /**
     * Invariants:
     * 1. if a Quest is deleted remove all references in all Rooms to the Quest
     * @param questPort
     */
    public void deleteQuest(QuestPort questPort);
}
