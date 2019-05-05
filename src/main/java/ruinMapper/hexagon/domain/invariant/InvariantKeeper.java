package ruinMapper.hexagon.domain.invariant;

import ruinMapper.hexagon.domain.model.ComponentSuper;
import ruinMapper.hexagon.domain.model.HasQuest;
import ruinMapper.hexagon.domain.model.HasRoom;
import ruinMapper.hexagon.domain.model.HasTag;
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
        HasTagManager {

    private RoomAndQuestDelegate roomAndQuestDelegate;
    private TagAndHasTagDelegate tagAndTaggableDelegate;

    private CRUDRepositoryPort<InvariantKeeper> invariantKeeperRepository;
    private UUID stateKeeperID;

    public InvariantKeeper(
            RoomAndQuestDelegate roomAndQuestDelegate,
            TagAndHasTagDelegate tagAndTaggableDelegate,
            CRUDRepositoryPort<InvariantKeeper> invariantKeeperRepository,
            UUID stateKeeperID) {
        this.roomAndQuestDelegate = roomAndQuestDelegate;
        this.tagAndTaggableDelegate = tagAndTaggableDelegate;
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
        roomAndQuestDelegate.addQuest(value, key);
        saveState();
    }

    @Override
    public void removeQuest(QuestPort value,
                            HasQuest key) {
        roomAndQuestDelegate.removeQuest(value, key);
        saveState();

    }

    @Override
    public Set<QuestPort> accessQuests(
            HasQuest hasQuest) {
        return roomAndQuestDelegate
                .accessQuests(hasQuest);
    }

    /**********************************************************/
// RoomManager
    @Override
    public void addRoom(RoomPort value,
                        HasRoom key) {
        roomAndQuestDelegate.addRoom(value, key);
        saveState();
    }

    @Override
    public void removeRoom(RoomPort value,
                           HasRoom key) {
        roomAndQuestDelegate.removeRoom(value, key);
        saveState();
    }

    @Override
    public Set<RoomPort> accessRooms(HasRoom hasRoom) {
        return roomAndQuestDelegate
                .accessRooms(hasRoom);
    }

    @Override
    public void deleteQuest(QuestPort questPort) {
        roomAndQuestDelegate.deleteQuest(questPort);
        saveState();
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
    //HasTagManager
    @Override
    public void deleteTag(TagPort tagPort) {
        tagAndTaggableDelegate.deleteTag(tagPort);
        saveState();
    }

    @Override
    public String toString() {
        return stateKeeperID.toString();
    }
}
