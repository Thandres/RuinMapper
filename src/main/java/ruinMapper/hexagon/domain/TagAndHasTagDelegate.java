package ruinMapper.hexagon.domain;

import ruinMapper.hexagon.domain.marker.ComponentType;
import ruinMapper.hexagon.domain.marker.HasTag;
import ruinMapper.hexagon.domain.tag.HasTagManager;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static ruinMapper.hexagon.domain.CircularManagmentHelper.*;

public class TagAndHasTagDelegate implements TagManager,
        HasTagManager {
    private Map<String, Set<TagPort>> roomToTagsMap;
    private Set<TagPort> contextTags;

    public TagAndHasTagDelegate(
            Map<String, Set<TagPort>> roomToTagsMap,
            Set<TagPort> contextTags) {
        this.roomToTagsMap = roomToTagsMap;

        this.contextTags = contextTags;
    }

    private void deleteTagImpl(TagPort tagPort) {
        contextTags.remove(tagPort);
        deleteRecord(roomToTagsMap, tagPort);
    }

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
    }

    @Override
    public Set<TagPort> accessTags(HasTag hasTag) {
        if (ComponentType.CONTEXT
                .equals(hasTag.getType())) {
            return new HashSet<>(contextTags);
        } else {
            return new HashSet<>(
                    roomToTagsMap.get(hasTag.toString()));
        }
    }

    @Override
    public void deleteTag(TagPort tagPort) {
        // Invariant 1
        deleteTagImpl(tagPort);
    }
}
