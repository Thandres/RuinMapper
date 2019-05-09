package ruinMapper.hexagon.domain.invariant;

import ruinMapper.hexagon.domain.area.AreaPort;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.model.*;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.RoomPort;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.awt.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static ruinMapper.hexagon.domain.invariant.CircularManagmentHelper.*;


/**
 * Class that implements all the Manager interfaces to keep the
 * invariants of the domain intact
 */
public class InvariantKeeper extends
        ComponentSuper implements
        QuestManager, RoomManager, TagManager,
        HasTagManager, HintManager, AreaManager {

    private Map<String, Set<QuestPort>> roomToQuestsMap;
    private Map<String, Set<RoomPort>> questToRoomsMap;
    private Map<String, Set<HintPort>> roomToHintsMap;
    private Map<String, RoomPort> hintToRoomMap;
    private Set<QuestPort> contextQuests;
    private Set<AreaPort> areaSet;
    private Map<String, Set<TagPort>> roomToTagsMap;
    private Set<TagPort> contextTags;

    private CRUDRepositoryPort<InvariantKeeper> invariantKeeperRepository;
    private UUID stateKeeperID;

    //TODO a MesseageQueue implementation of this mess to make usage of managers cleaner
    public InvariantKeeper(

            Map<String, Set<QuestPort>> roomToQuestsMap,
            Map<String, Set<RoomPort>> questToRoomsMap,
            Map<String, Set<HintPort>> roomToHintsMap,
            Map<String, RoomPort> hintToRoomMap,
            Set<QuestPort> contextQuests,
            Set<AreaPort> areaSet,
            Map<String, Set<TagPort>> roomToTagsMap,
            Set<TagPort> contextTags,
            CRUDRepositoryPort<InvariantKeeper> invariantKeeperRepository,
            UUID stateKeeperID) {
        this.roomToQuestsMap = roomToQuestsMap;
        this.questToRoomsMap = questToRoomsMap;
        this.roomToHintsMap = roomToHintsMap;
        this.hintToRoomMap = hintToRoomMap;
        this.contextQuests = contextQuests;
        this.areaSet = areaSet;
        this.roomToTagsMap = roomToTagsMap;
        this.contextTags = contextTags;

        this.invariantKeeperRepository = invariantKeeperRepository;
        this.stateKeeperID = stateKeeperID;
    }

    private void deleteRoomImpl(RoomPort roomToDelete) {
        deleteLinkedRecord(roomToQuestsMap, questToRoomsMap,
                roomToDelete);
        Set<HintPort> set = roomToHintsMap
                .remove(roomToDelete.toString());
        if (set != null) {
            for (HintPort hint : set) {
                hintToRoomMap.remove(hint.toString());
            }
        }
        Point point = roomToDelete.accessCoordinates();
        Set<AreaPort> areaPortSet = areaSet.stream()
                .filter(areaPort -> areaPort.accessRooms()
                        .contains(roomToDelete))
                .collect(Collectors.toSet());
        for (AreaPort area : areaPortSet) {
            area.deleteRoom(point.x, point.y);
        }

    }

    private void deleteQuestImpl(QuestPort questToDelete) {
        contextQuests.remove(questToDelete);
        deleteLinkedRecord(questToRoomsMap,
                roomToQuestsMap, questToDelete);
    }

    private void deleteHintImpl(HintPort hintPort) {
        // Own implementation because of
        hintToRoomMap.remove(hintPort.toString());
        deleteRecord(roomToHintsMap, hintPort);
    }

    private void deleteAreaImpl(AreaPort areaToDelete) {
        areaSet.remove(areaToDelete);
    }

    private void deleteTagImpl(TagPort tagPort) {
        contextTags.remove(tagPort);
        deleteRecord(roomToTagsMap, tagPort);
    }

    private void deleteAll() {
        roomToQuestsMap.clear();
        roomToHintsMap.clear();
        questToRoomsMap.clear();
        contextQuests.clear();
        hintToRoomMap.clear();
        areaSet.clear();
        contextTags.clear();
        roomToTagsMap.clear();
    }

    @Override
    public void saveState() {
        invariantKeeperRepository.update(this);
    }

    /*******************************************************/

    // QuestManager
    @Override
    public void addQuest(QuestPort value,
                         HasQuest key) {
        // Invariant 1
        if (ComponentType.ROOM.equals(key.getType())) {
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
                            HasQuest key) {
        if (ComponentType.ROOM.equals(key.getType())) {
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
            HasQuest hasQuest) {
        if (ComponentType.ROOM
                .equals(hasQuest.getType())) {
            if (!containsKey(
                    roomToQuestsMap, hasQuest)) {
                roomToQuestsMap.put(hasQuest.toString(),
                        new HashSet<>());
            }
            return new HashSet<>(roomToQuestsMap
                    .get(hasQuest.toString()));
        } else if (ComponentType.CONTEXT
                .equals(hasQuest.getType())) {
            return new HashSet<>(contextQuests);
        } else {
            return new HashSet<>();
        }
    }

    /**********************************************************/
// RoomManager
    @Override
    public void addRoom(RoomPort value,
                        HasRoom key) {
        if (ComponentType.QUEST.equals(key.getType())) {
            // Invariant 1
            linkedAdd(questToRoomsMap, roomToQuestsMap,
                    (QuestPort) key,
                    value);
        } else {
            // Invariant 2 and 3
            hintToRoomMap.put(key.toString(), value);
            addToSetMap(roomToHintsMap, value.toString(),
                    (HintPort) key);
        }
        saveState();
    }

    @Override
    public void removeRoom(RoomPort value,
                           HasRoom key) {
        if (ComponentType.QUEST.equals(key.getType())) {
            // Invariant 1
            linkedRemove(questToRoomsMap, roomToQuestsMap,
                    (QuestPort) key,
                    value);
        } else {
            // Invariant 2
            hintToRoomMap.remove(key.toString());
        }
        saveState();
    }

    @Override
    public Set<RoomPort> accessRooms(HasRoom hasRoom) {
        if (ComponentType.QUEST.equals(hasRoom
                .getType())) {
            if (!containsKey(questToRoomsMap,
                    hasRoom)) {
                questToRoomsMap.put(hasRoom.toString(),
                        new HashSet<>());
            }
            return new HashSet<>(
                    questToRoomsMap
                            .get(hasRoom.toString()));
        } else if (ComponentType.HINT
                .equals(hasRoom.getType())) {
            if (!hintToRoomMap
                    .containsKey(hasRoom.toString())) {
                hintToRoomMap.put(hasRoom.toString(), null);
            }
            RoomPort room = hintToRoomMap
                    .get(hasRoom.toString());
            Set<RoomPort> temp = new HashSet<>();
            temp.add(room);
            return temp;
        } else {
            return new HashSet<>();
        }
    }


    /**********************************************************/
// TagManager
    @Override
    public void addTag(TagPort value, HasTag key) {
        if (ComponentType.CONTEXT.equals(key.getType())) {
            // Invariant 1
            contextTags.add(value);
        } else {
            // Invariant 2
            if (contextTags.contains(value)) {
                addToSetMap(roomToTagsMap, key.toString(),
                        value);
            } else {
                //TODO invalid tag handling
            }
        }
        saveState();
    }

    @Override
    public void removeTag(TagPort value, HasTag key) {
        if (ComponentType.CONTEXT.equals(key.getType())) {
            // Invariant 1
            deleteTagImpl(value);
        } else {
            // Invariant 2
            removeFromMap(roomToTagsMap, key.toString(),
                    value);
        }
        saveState();
    }

    @Override
    public Set<TagPort> accessTags(HasTag hasTag) {
        if (ComponentType.CONTEXT
                .equals(hasTag.getType())) {
            return new HashSet<>(contextTags);
        } else {
            if (!containsKey(roomToTagsMap, hasTag)) {
                roomToTagsMap.put(hasTag.toString(),
                        new HashSet<>());
            }
            return new HashSet<>(
                    roomToTagsMap.get(hasTag.toString()));
        }
    }

    /**********************************************************/
    // ComponentSuper
    @Override
    public String toString() {
        return stateKeeperID.toString();
    }

    /**********************************************************/
    //HintManager
    @Override
    public void addHint(HintPort value, HasHint key) {
        addToSetMap(roomToHintsMap, key.toString(), value);
        if (ComponentType.ROOM.equals(key.getType())) {
            hintToRoomMap
                    .put(value.toString(), (RoomPort) key);
        }
        saveState();
    }

    @Override
    public void removeHint(HintPort value, HasHint key) {
        if (ComponentType.ROOM.equals(key.getType())) {
            deleteHintImpl(value);
        } else {
            removeFromMap(roomToHintsMap, key.toString(),
                    value);
        }
        saveState();
    }

    @Override
    public Set<HintPort> accessHints(HasHint hasHint) {
        if (!containsKey(roomToHintsMap, hasHint)) {
            roomToHintsMap.put(hasHint.toString(),
                    new HashSet<>());
        }
        return new HashSet<>(
                roomToHintsMap.get(hasHint.toString()));
    }

    /**********************************************************/
    // AreaManager
    @Override
    public void addArea(AreaPort value, HasArea key) {
        areaSet.add(value);
        saveState();
    }

    @Override  // TODO never used, worth implementing?
    public void removeArea(AreaPort value, HasArea key) {
        areaSet.remove(value);
        // TODO remove all rooms referenced in value
        saveState();
    }

    @Override
    public Set<AreaPort> accessAreas(HasArea hasArea) {
        return new HashSet<>(areaSet);
    }

    /**********************************************************/
    @Override
    public <T extends ComponentTag> void deleteManagedObject(
            T managedObject) {
        switch (managedObject.getType()) {
            case QUEST:
                deleteQuestImpl((QuestPort) managedObject);
                break;
            case HINT:
                deleteHintImpl((HintPort) managedObject);
                break;
            case ROOM:
                deleteRoomImpl((RoomPort) managedObject);
                break;
            case AREA:
                deleteAreaImpl((AreaPort) managedObject);
                break;
            case TAG:
                deleteTagImpl((TagPort) managedObject);
                break;
            case CONTEXT:
                deleteAll();
        }
        if (managedObject.getType()
                .equals(ComponentType.CONTEXT)) {
            invariantKeeperRepository
                    .delete(stateKeeperID.toString());
        } else {
            saveState();
        }
    }

    public Map<String, Set<QuestPort>> getRoomToQuestsMap() {
        return roomToQuestsMap;
    }

    public void setRoomToQuestsMap(
            Map<String, Set<QuestPort>> roomToQuestsMap) {
        this.roomToQuestsMap = roomToQuestsMap;
    }

    public Map<String, Set<RoomPort>> getQuestToRoomsMap() {
        return questToRoomsMap;
    }

    public void setQuestToRoomsMap(
            Map<String, Set<RoomPort>> questToRoomsMap) {
        this.questToRoomsMap = questToRoomsMap;
    }

    public Map<String, Set<HintPort>> getRoomToHintsMap() {
        return roomToHintsMap;
    }

    public void setRoomToHintsMap(
            Map<String, Set<HintPort>> roomToHintsMap) {
        this.roomToHintsMap = roomToHintsMap;
    }

    public Map<String, RoomPort> getHintToRoomMap() {
        return hintToRoomMap;
    }

    public void setHintToRoomMap(
            Map<String, RoomPort> hintToRoomMap) {
        this.hintToRoomMap = hintToRoomMap;
    }

    public Set<QuestPort> getContextQuests() {
        return contextQuests;
    }

    public void setContextQuests(
            Set<QuestPort> contextQuests) {
        this.contextQuests = contextQuests;
    }

    public Set<AreaPort> getAreaSet() {
        return areaSet;
    }

    public void setAreaSet(
            Set<AreaPort> areaSet) {
        this.areaSet = areaSet;
    }

    public Map<String, Set<TagPort>> getRoomToTagsMap() {
        return roomToTagsMap;
    }

    public void setRoomToTagsMap(
            Map<String, Set<TagPort>> roomToTagsMap) {
        this.roomToTagsMap = roomToTagsMap;
    }

    public Set<TagPort> getContextTags() {
        return contextTags;
    }

    public void setContextTags(
            Set<TagPort> contextTags) {
        this.contextTags = contextTags;
    }

    public UUID getStateKeeperID() {
        return stateKeeperID;
    }

    public void setStateKeeperID(UUID stateKeeperID) {
        this.stateKeeperID = stateKeeperID;
    }

}
