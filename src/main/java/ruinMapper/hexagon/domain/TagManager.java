package ruinMapper.hexagon.domain;

import ruinMapper.hexagon.domain.tag.TagPort;

import java.util.Set;

/**
 * Interface used by Taggable objects for managing their tags
 * and ensuring invariants concerning this behavoiur
 */
public interface TagManager {

    public void addTag(TagPort value, Taggable key);

    public void removeTag(TagPort value, Taggable key);

    public Set<TagPort> accessTags(Taggable taggable);
}
