package ruinMapper.hexagon.domain.tag;

/**
 * Interface that keeps the invariants of Tags intact
 */
public interface TaggableManager {

    // Invariant:
    // When a Tag is deleted make sure every Taggable removes
    // the reference to the removed Tag
    public void deleteTag(TagPort tagPort);
}
