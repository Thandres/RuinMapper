package ruinMapper.hexagon.domain.room;

import ruinMapper.hexagon.domain.quest.QuestPort;

import java.util.Set;

/**
 * Abstracts the quest functionality away from RoomPort
 */
public interface QuestManager {

    public void addQuest(QuestPort value,
                         RoomPort key);

    public void removeQuest(QuestPort value,
                            RoomPort key);

    public Set<QuestPort> accessQuests(
            RoomPort roomPort);

}
