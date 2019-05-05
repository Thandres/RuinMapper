package ruinMapper.hexagon.domain;

import ruinMapper.hexagon.domain.room.RoomPort;
import ruinMapper.hexagon.domain.tag.TagPort;
import ruinMapper.hexagon.domain.tag.TaggableManager;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static ruinMapper.hexagon.domain.CircularManagmentHelper.*;

public class TagAndTaggableDelegate implements TagManager,
        TaggableManager {
    private Map<RoomPort, Set<TagPort>> roomToTagsMap;
    private Set<TagPort> contextTags;

    public TagAndTaggableDelegate(
            Map<RoomPort, Set<TagPort>> roomToTagsMap,
            Set<TagPort> contextTags) {
        this.roomToTagsMap = roomToTagsMap;

        this.contextTags = contextTags;
    }

    private void deleteTagImpl(TagPort tagPort) {
        contextTags.remove(tagPort);
        deleteRecord(roomToTagsMap, tagPort);
    }

    @Override
    public void addTag(TagPort value, Taggable key) {
        if (key.isContext()) {
            // Invariant 1
            contextTags.add(value);
        } else {
            // Invariant 2
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
        if (key.isContext()) {
            // Invariant 1
            deleteTagImpl(value);
        } else {
            // Invariant 2
            removeFromMap(roomToTagsMap, (RoomPort) key,
                    value);
        }
    }

    @Override
    public Set<TagPort> accessTags(Taggable taggable) {
        if (taggable.isContext()) {
            return new HashSet<>(contextTags);
        } else {
            return new HashSet<>(
                    roomToTagsMap.get((RoomPort) taggable));
        }
    }

    @Override
    public void deleteTag(TagPort tagPort) {
        // Invariant 1
        deleteTagImpl(tagPort);
    }
}
