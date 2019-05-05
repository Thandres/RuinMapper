package ruinMapper.hexagon.domain;

import ruinMapper.hexagon.domain.marker.HasTag;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.util.Set;

/**
 * Interface that keeps the Invariants of Taggables intact
 */
public interface TagManager {

    /**
     * Invariants:
     * 1. If the HasTag is a Context, add the Tag to the possible Tags
     * 2. If the HasTag is a Room, add the Tag if it is a possible Tag
     *
     * @param value The Tag to add
     * @param key   The HasTag, either Room or Context
     */
    public void addTag(TagPort value, HasTag key);

    /**
     * Invariants:
     * 1. If the HasTag is a Context, remove all references to the Tag in all Taggables
     * 2. If the HasTag is a Room, remove the Tag from the HasTag
     *
     * @param value
     * @param key
     */
    public void removeTag(TagPort value, HasTag key);

    /**
     * Return the Tags assigned to the HasTag
     *
     * @param hasTag
     * @return
     */
    public Set<TagPort> accessTags(HasTag hasTag);
}
