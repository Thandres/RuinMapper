package ruinMapper.hexagon.domain.invariant;

import ruinMapper.hexagon.domain.tag.TagPort;

/**
 * Interface that keeps the invariants of Tags intact
 */
public interface HasTagManager {

    // Invariant:
    // When a Tag is deleted make sure every HasTag removes
    // the reference to the removed Tag
    public void deleteTag(TagPort tagPort);
}