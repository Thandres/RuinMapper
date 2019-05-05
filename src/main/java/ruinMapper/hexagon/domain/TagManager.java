package ruinMapper.hexagon.domain;

import ruinMapper.hexagon.domain.tag.TagPort;

import java.util.Set;

/**
 * Interface that keeps the Invariants of Taggables intact
 */
public interface TagManager {

    /**
     * Invariants:
     * 1. If the Taggable is a Context, add the Tag to the possible Tags
     * 2. If the Taggable is a Room, add the Tag if it is a possible Tag
     *
     * @param value The Tag to add
     * @param key   The Taggable, either Room or Context
     */
    public void addTag(TagPort value, Taggable key);

    /**
     * Invariants:
     * 1. If the Taggable is a Context, remove all references to the Tag in all Taggables
     * 2. If the Taggable is a Room, remove the Tag from the Taggable
     *
     * @param value
     * @param key
     */
    public void removeTag(TagPort value, Taggable key);

    /**
     * Return the Tags assigned to the Taggable
     *
     * @param taggable
     * @return
     */
    public Set<TagPort> accessTags(Taggable taggable);
}
