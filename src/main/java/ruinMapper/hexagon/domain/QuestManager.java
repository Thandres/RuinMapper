package ruinMapper.hexagon.domain;

import ruinMapper.hexagon.domain.quest.QuestPort;

import java.util.Set;

/**
 * Abstracts the quest functionality away from RoomPort
 */
public interface QuestManager {

    public void addQuest(QuestPort value,
                         Questable key);

    public void removeQuest(QuestPort value,
                            Questable key);

    public Set<QuestPort> accessQuests(
            Questable questable);

}
