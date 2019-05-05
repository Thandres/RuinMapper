package ruinMapper.hexagon.domain;

import ruinMapper.hexagon.domain.quest.QuestPort;

import java.util.Set;

/**
 * Interface that keeps the Invariants of Questables intact
 */
public interface QuestManager {

    /**
     * Invariant:
     * 1. when a Quest is added to the Questable and the Questable is a Room,
     * add the Room to the Quest as well
     *
     * @param value the quest to add
     * @param key   the questable, either Room or Context
     */
    public void addQuest(QuestPort value,
                         Questable key);

    /**
     * Invariant:
     * 1. When a Quest is removed from a Questable, remove the Questable from the Quest.
     * 2. If the Questable is a Context, remove the Quest from all other Questables
     *
     * @param value the Quest to remove
     * @param key   the Questable, either Room or Context
     */
    public void removeQuest(QuestPort value,
                            Questable key);

    /**
     * Return the Set of Quests assigned to the Questable
     * @param questable
     * @return
     */
    public Set<QuestPort> accessQuests(
            Questable questable);

}
