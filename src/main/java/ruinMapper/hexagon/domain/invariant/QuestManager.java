package ruinMapper.hexagon.domain.invariant;

import ruinMapper.hexagon.domain.model.HasQuest;
import ruinMapper.hexagon.domain.quest.QuestPort;

import java.util.Set;

/**
 * Interface that keeps the Invariants of Questables intact
 */
public interface QuestManager {

    /**
     * Invariant:
     * 1. when a Quest is added to the HasQuest and the HasQuest is a Room,
     * add the Room to the Quest as well
     *
     * @param value the quest to add
     * @param key   the questable, either Room or Context
     */
    public void addQuest(QuestPort value,
                         HasQuest key);

    /**
     * Invariant:
     * 1. When a Quest is removed from a HasQuest, remove the HasQuest from the Quest.
     * 2. If the HasQuest is a Context, remove the Quest from all other Questables
     *
     * @param value the Quest to remove
     * @param key   the HasQuest, either Room or Context
     */
    public void removeQuest(QuestPort value,
                            HasQuest key);

    /**
     * Return the Set of Quests assigned to the HasQuest
     * @param hasQuest
     * @return
     */
    public Set<QuestPort> accessQuests(
            HasQuest hasQuest);

}
