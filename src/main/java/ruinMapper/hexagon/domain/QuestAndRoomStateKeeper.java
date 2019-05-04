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


public class QuestAndRoomStateKeeper implements
        QuestManager, RoomManager, TagManager,
        TaggableManager {
    private Map<RoomPort, Set<QuestPort>> roomToQuestsMap;
    private Map<QuestPort, Set<RoomPort>> questToRoomsMap;
    private Set<QuestPort> contextQuests;


    //TODO implement the TaggableManager and TagManager interface
    private Map<Taggable, Set<TagPort>> taggableToTagsMap;
    private Map<TagPort, Set<RoomPort>> roomToTagsMap;

    private CRUDRepositoryPort<QuestAndRoomStateKeeper> stateRepository;
    private UUID stateKeeperID;

    public QuestAndRoomStateKeeper(
            Map<RoomPort, Set<QuestPort>> roomToQuestsMap,
            Map<QuestPort, Set<RoomPort>> questToRoomsMap,
            Set<QuestPort> contextQuests,
            CRUDRepositoryPort<QuestAndRoomStateKeeper> stateRepository,
            UUID stateKeeperID) {

        this.roomToQuestsMap = roomToQuestsMap;
        this.questToRoomsMap = questToRoomsMap;
        this.contextQuests = contextQuests;
        this.stateRepository = stateRepository;
        this.stateKeeperID = stateKeeperID;
    }

    // QuestManager
    @Override
    public void addQuest(QuestPort value,
                         Questable key) {
        if (!key.isContext()) {
            addToMap(roomToQuestsMap, (RoomPort) key,
                    value);
            addToMap(questToRoomsMap, value,
                    (RoomPort) key);
        }
        contextQuests.add(value);
        saveState();
    }

    @Override
    public void removeQuest(QuestPort value,
                            Questable key) {
        if (!key.isContext()) {
            removeFromMap(roomToQuestsMap, (RoomPort) key,
                    value);
            removeFromMap(questToRoomsMap, value,
                    (RoomPort) key);
        }
        contextQuests.remove(value);
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
        addToMap(questToRoomsMap, key, value);
        addToMap(roomToQuestsMap, value, key);
        saveState();
    }

    @Override
    public void removeRoom(RoomPort value,
                           QuestPort key) {
        removeFromMap(questToRoomsMap, key, value);
        removeFromMap(roomToQuestsMap, value, key);
        saveState();
    }

    @Override
    public Set<RoomPort> accessRooms(QuestPort questPort) {
        return questToRoomsMap.get(questPort);
    }

    @Override
    public void deleteQuest(QuestPort questPort) {
        deleteRecord(questToRoomsMap, roomToQuestsMap,
                questPort);
        saveState();
    }

    /**********************************************************/

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

    private <T, D> void removeFromMap(Map<T, Set<D>> map,
                                      T key, D value) {
        if (map.containsKey(key)) {
            map.get(key).remove(
                    value);
        } else {
            //TODO Exceptions for everything
        }
    }

    private <T, D> void deleteRecord(Map<T, Set<D>> tSetMap,
                                     Map<D, Set<T>> dSetMap,
                                     T recordToDelete) {
        if (tSetMap.containsKey(recordToDelete)) {
            tSetMap.remove(recordToDelete)
                    .forEach(d -> dSetMap.get(d)
                            .remove(recordToDelete));
        }
    }

    private void saveState() {
        stateRepository.update(this);
    }

    @Override
    public void addTag(TagPort value, Taggable key) {

    }

    @Override
    public void removeTag(TagPort value, Taggable key) {

    }

    @Override
    public Set<TagPort> accessTags(Taggable taggable) {
        return null;
    }

    @Override
    public void deleteTag(TagPort tagPort) {

    }
}
