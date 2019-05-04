package ruinMapper.hexagon.domain;

import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.quest.RoomManager;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.QuestManager;
import ruinMapper.hexagon.domain.room.RoomPort;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


public class QuestAndRoomStateKeeper implements
        QuestManager, RoomManager {
    private Map<RoomPort, Set<QuestPort>> roomToQuestsMap;

    private Map<QuestPort, Set<RoomPort>> questToRoomsMap;
    private CRUDRepositoryPort<QuestAndRoomStateKeeper> stateRepository;
    private UUID stateKeeperID;

    public QuestAndRoomStateKeeper(
            Map<RoomPort, Set<QuestPort>> roomToQuestsMap,
            Map<QuestPort, Set<RoomPort>> questToRoomsMap,
            CRUDRepositoryPort<QuestAndRoomStateKeeper> stateRepository,
            UUID stateKeeperID) {

        this.roomToQuestsMap = roomToQuestsMap;
        this.questToRoomsMap = questToRoomsMap;
        this.stateRepository = stateRepository;
        this.stateKeeperID = stateKeeperID;
    }

    // QuestManager
    @Override
    public void addQuest(QuestPort value,
                         RoomPort key) {
        addToMap(roomToQuestsMap, key,
                value);
        addToMap(questToRoomsMap, value, key);
        saveState();
    }

    @Override
    public void removeQuest(QuestPort value,
                            RoomPort key) {
        removeFromMap(roomToQuestsMap, key,
                value);
        removeFromMap(questToRoomsMap, value, key);
        saveState();

    }

    @Override
    public Set<QuestPort> accessQuests(RoomPort roomPort) {
        return roomToQuestsMap.get(roomPort);
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
}
