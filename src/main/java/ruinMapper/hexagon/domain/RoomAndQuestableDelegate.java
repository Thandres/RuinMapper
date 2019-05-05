package ruinMapper.hexagon.domain;

import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.quest.RoomManager;
import ruinMapper.hexagon.domain.room.RoomPort;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static ruinMapper.hexagon.domain.CircularManagmentHelper.*;

// Handles the Questable and Roomable Invariants
public class RoomAndQuestableDelegate implements
        QuestManager,
        RoomManager {
    private Map<RoomPort, Set<QuestPort>> roomToQuestsMap;
    private Map<QuestPort, Set<RoomPort>> questToRoomsMap;
    private Set<QuestPort> contextQuests;

    public RoomAndQuestableDelegate(
            Map<RoomPort, Set<QuestPort>> roomToQuestsMap,
            Map<QuestPort, Set<RoomPort>> questToRoomsMap,
            Set<QuestPort> contextQuests) {
        this.roomToQuestsMap = roomToQuestsMap;
        this.questToRoomsMap = questToRoomsMap;
        this.contextQuests = contextQuests;
    }

    private void deleteQuestImpl(QuestPort questToDelete) {
        contextQuests.remove(questToDelete);
        deleteLinkedRecord(questToRoomsMap,
                roomToQuestsMap, questToDelete);
    }


    @Override
    public void addQuest(QuestPort value, Questable key) {
        // Invariant 1
        if (!key.isContext()) {
            linkedAdd(roomToQuestsMap, questToRoomsMap,
                    (RoomPort) key, value);
        }
        // cant add duplicate objects to a set, so adding
        // is either successful or the object is already there
        contextQuests.add(value);
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

    @Override
    public void addRoom(RoomPort value, QuestPort key) {
        // Invariant 1
        linkedAdd(questToRoomsMap, roomToQuestsMap, key,
                value);
    }

    @Override
    public void removeRoom(RoomPort value, QuestPort key) {
        // Invariant 1
        linkedRemove(questToRoomsMap, roomToQuestsMap, key,
                value);
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
    }
}
