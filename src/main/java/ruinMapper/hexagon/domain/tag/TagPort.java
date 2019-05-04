package ruinMapper.hexagon.domain.tag;

import ruinMapper.hexagon.domain.TaggableManager;

/**
 * Something that marks a room, i.e. a shop, or a character
 * enables the user to use custom tags, to mark rooms with any
 * information they wish
 */
public interface TagPort extends TaggableManager {
    public String accessTag();

    public void changeTag(String newText);

    public void deleteTag();

}
