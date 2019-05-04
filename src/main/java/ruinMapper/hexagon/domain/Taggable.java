package ruinMapper.hexagon.domain;

import ruinMapper.hexagon.domain.tag.TagPort;

import java.util.Set;

/**
 * A taggable object is responsible for holding information
 * of its tags and upon adding
 * tags register themselves to the tag
 */
public interface Taggable {

    public void addTag(TagPort port);

    public void removeTag(TagPort port);

    public Set<TagPort> accessTags();
}
