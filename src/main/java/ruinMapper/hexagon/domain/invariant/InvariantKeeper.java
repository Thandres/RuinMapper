package ruinMapper.hexagon.domain.invariant;

import ruinMapper.hexagon.domain.area.AreaPort;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.model.*;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.RoomPort;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.util.Set;
import java.util.UUID;


/**
 * Class that implements all the Manager interfaces to keep the
 * invariants of the domain intact
 */
public class InvariantKeeper extends
        ComponentSuper implements
        QuestManager, RoomManager, TagManager,
        HasTagManager, HintManager, AreaManager {

    private RoomAndQuestAndHintDelegate roomAndQuestAndHintDelegate;
    private TagAndHasTagDelegate tagAndTaggableDelegate;
    private AreaDelegate areaDelegate;
    private CRUDRepositoryPort<InvariantKeeper> invariantKeeperRepository;
    private UUID stateKeeperID;

    public InvariantKeeper(
            RoomAndQuestAndHintDelegate roomAndQuestAndHintDelegate,
            TagAndHasTagDelegate tagAndTaggableDelegate,
            AreaDelegate areaDelegate,
            CRUDRepositoryPort<InvariantKeeper> invariantKeeperRepository,
            UUID stateKeeperID) {
        this.roomAndQuestAndHintDelegate = roomAndQuestAndHintDelegate;
        this.tagAndTaggableDelegate = tagAndTaggableDelegate;
        this.areaDelegate = areaDelegate;
        this.invariantKeeperRepository = invariantKeeperRepository;
        this.stateKeeperID = stateKeeperID;
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
        roomAndQuestAndHintDelegate.addQuest(value, key);
        saveState();
    }

    @Override
    public void removeQuest(QuestPort value,
                            HasQuest key) {
        roomAndQuestAndHintDelegate.removeQuest(value, key);
        saveState();

    }

    @Override
    public Set<QuestPort> accessQuests(
            HasQuest hasQuest) {
        return roomAndQuestAndHintDelegate
                .accessQuests(hasQuest);
    }

    /**********************************************************/
// RoomManager
    @Override
    public void addRoom(RoomPort value,
                        HasRoom key) {
        roomAndQuestAndHintDelegate.addRoom(value, key);
        saveState();
    }

    @Override
    public void removeRoom(RoomPort value,
                           HasRoom key) {
        roomAndQuestAndHintDelegate.removeRoom(value, key);
        saveState();
    }

    @Override
    public Set<RoomPort> accessRooms(HasRoom hasRoom) {
        return roomAndQuestAndHintDelegate
                .accessRooms(hasRoom);
    }


    /**********************************************************/
// TagManager
    @Override
    public void addTag(TagPort value, HasTag key) {
        tagAndTaggableDelegate.addTag(value, key);
        saveState();
    }

    @Override
    public void removeTag(TagPort value, HasTag key) {
        tagAndTaggableDelegate.removeTag(value, key);
        saveState();
    }

    @Override
    public Set<TagPort> accessTags(HasTag hasTag) {
        return tagAndTaggableDelegate.accessTags(hasTag);
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
        roomAndQuestAndHintDelegate.addHint(value, key);
        saveState();
    }

    @Override
    public void removeHint(HintPort value, HasHint key) {
        roomAndQuestAndHintDelegate.removeHint(value, key);
        saveState();
    }

    @Override
    public Set<HintPort> accessHints(HasHint hasHint) {
        return roomAndQuestAndHintDelegate
                .accessHints(hasHint);
    }

    /**********************************************************/
    // AreaManager
    @Override
    public void addArea(AreaPort value, HasArea key) {
        areaDelegate.addArea(value, key);
        saveState();
    }

    @Override
    public void removeArea(AreaPort value, HasArea key) {
        areaDelegate.removeArea(value, key);
        saveState();
    }

    @Override
    public Set<AreaPort> accessAreas(HasArea hasArea) {
        return areaDelegate.accessAreas(hasArea);
    }

    /**********************************************************/
    @Override
    public <T extends ComponentTag> void deleteManagedObject(
            T managedObject) {
        switch (managedObject.getType()) {
            case QUEST:// Same as Hint
            case HINT:
                roomAndQuestAndHintDelegate
                        .deleteManagedObject(managedObject);
                break;
            case ROOM:
                roomAndQuestAndHintDelegate
                        .deleteManagedObject(managedObject);
                tagAndTaggableDelegate
                        .deleteManagedObject(managedObject);
                break;
            case TAG:
                tagAndTaggableDelegate
                        .deleteManagedObject(managedObject);
                break;
            case AREA://TODO
            case CONTEXT://TODO, also necessary? Special case or just ignore, because when the context gets deleted the main entry point just references another context
                roomAndQuestAndHintDelegate
                        .deleteManagedObject(managedObject);
        }
        saveState();
    }
}
