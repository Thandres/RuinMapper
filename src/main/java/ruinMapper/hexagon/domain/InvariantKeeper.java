package ruinMapper.hexagon.domain;

import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.quest.RoomManager;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.RoomPort;
import ruinMapper.hexagon.domain.tag.TagPort;
import ruinMapper.hexagon.domain.tag.TaggableManager;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


/**
 * Class that implements all the Manager interfaces to keep the
 * invariants of the domain intact
 */
public class InvariantKeeper implements
        QuestManager, RoomManager, TagManager,
        TaggableManager {
    //TODO implement the IDs of component in a pre/postfix way so the maps can save strings instead of whole objects
    private Map<RoomPort, Set<QuestPort>> roomToQuestsMap;
    private Map<QuestPort, Set<RoomPort>> questToRoomsMap;
    private Set<QuestPort> contextQuests;


    //TODO implement the TaggableManager and TagManager interface
    private Map<RoomPort, Set<TagPort>> roomToTagsMap;
    private Map<TagPort, Set<RoomPort>> tagToRoomsMap;
    private Set<TagPort> contextTags;

    private CRUDRepositoryPort<InvariantKeeper> stateRepository;
    private UUID stateKeeperID;

    public InvariantKeeper(
            Map<RoomPort, Set<QuestPort>> roomToQuestsMap,
            Map<QuestPort, Set<RoomPort>> questToRoomsMap,
            Set<QuestPort> contextQuests,
            CRUDRepositoryPort<InvariantKeeper> stateRepository,
            UUID stateKeeperID) {

        this.roomToQuestsMap = roomToQuestsMap;
        this.questToRoomsMap = questToRoomsMap;
        this.contextQuests = contextQuests;
        this.stateRepository = stateRepository;
        this.stateKeeperID = stateKeeperID;
    }

    private void saveState() {
        stateRepository.update(this);
    }

    private <T, D> void removeFromMap(Map<T, Set<D>> map,
                                      T key, D value) {
        if (map.containsKey(key)) {
            map.get(key).remove(
                    value);
        } else {
            //TODO Exceptions for everything
        }
    }

    private <T, D> void linkedRemove(Map<T, Set<D>> tMap,
                                     Map<D, Set<T>> dMap,
                                     T key, D value) {
        removeFromMap(tMap, key, value);
        removeFromMap(dMap, value, key);
    }

    private <T, D> void addToMap(
            Map<T, Set<D>> map, T key, D value) {
        if (map.containsKey(key)) {
            map.get(key).add(
                    value);
        } else {
            map.put(key,
                    new HashSet<>());
            map.get(key).add(value);
        }
    }

    private <T, D> void linkedAdd(Map<T, Set<D>> tMap,
                                  Map<D, Set<T>> dMap,
                                  T key, D value) {
        addToMap(tMap, key, value);
        addToMap(dMap, value, key);
    }

    // deletes recordToDelete from every Set in dSetMap
    private <T, D> void deleteRecord(Map<D, Set<T>> dSetMap,
                                     T recordToDelete) {
        for (Set<T> tSet : dSetMap.values()) {
            tSet.remove(recordToDelete);
        }
    }

    private <T, D> void deleteLinkedRecord(
            Map<T, Set<D>> tSetMap,
            Map<D, Set<T>> dSetMap,
            T recordToDelete) {
        if (tSetMap.containsKey(recordToDelete)) {
            tSetMap.remove(recordToDelete)
                    .forEach(d -> removeFromMap(dSetMap, d,
                            recordToDelete));
        }
    }

    private void deleteQuestImpl(QuestPort questToDelete) {
        contextQuests.remove(questToDelete);
        deleteLinkedRecord(questToRoomsMap,
                roomToQuestsMap, questToDelete);
    }
    //Interface implementations

    /*******************************************************/

    // QuestManager
    @Override
    public void addQuest(QuestPort value,
                         Questable key) {
        if (!key.isContext()) {
            linkedAdd(roomToQuestsMap, questToRoomsMap,
                    (RoomPort) key, value);
        }
        // cant add duplicate objects to a set, so adding
        // is either successful or the object is already there
        contextQuests.add(value);
        saveState();
    }

    @Override
    public void removeQuest(QuestPort value,
                            Questable key) {
        if (!key.isContext()) {
            // Invariant 1
            linkedRemove(roomToQuestsMap, questToRoomsMap,
                    (RoomPort) key, value);
        } else {
            // Invariant 2
            deleteQuestImpl(value);
        }
        saveState();

    }

    @Override
    public Set<QuestPort> accessQuests(
            Questable questable) {
        if (!questable.isContext()) {
            return new HashSet<>(roomToQuestsMap
                    .get((RoomPort) questable));
        } else {
            return new HashSet<>(contextQuests);
        }
    }

    /**********************************************************/
// RoomManager
    @Override
    public void addRoom(RoomPort value,
                        QuestPort key) {
        // Invariant 1
        linkedAdd(questToRoomsMap, roomToQuestsMap, key,
                value);
        saveState();
    }

    @Override
    public void removeRoom(RoomPort value,
                           QuestPort key) {
        // Invariant 1
        linkedRemove(questToRoomsMap, roomToQuestsMap, key,
                value);
        saveState();
    }

    @Override
    public Set<RoomPort> accessRooms(QuestPort questPort) {
        return new HashSet<>(
                questToRoomsMap.get(questPort));
    }

    @Override
    public void deleteQuest(QuestPort questPort) {
        // Invariant 1
        deleteQuestImpl(questPort);
        saveState();
    }

    /**********************************************************/
// TagManager
    @Override
    public void addTag(TagPort value, Taggable key) {
        if (key.isContext()) {
            contextTags.add(value);
        } else {
            if (contextTags.contains(value)) {
                addToMap(roomToTagsMap, (RoomPort) key,
                        value);
            } else {
                //TODO invalid tag handling
            }
        }
    }

    @Override
    public void removeTag(TagPort value, Taggable key) {

    }

    @Override
    public Set<TagPort> accessTags(Taggable taggable) {
        return null;
    }

    /**********************************************************/
    //TaggableManager
    @Override
    public void deleteTag(TagPort tagPort) {
        contextTags.remove(tagPort);
        deleteLinkedRecord(tagToRoomsMap, roomToTagsMap,
                tagPort);
        saveState();
    }
}
