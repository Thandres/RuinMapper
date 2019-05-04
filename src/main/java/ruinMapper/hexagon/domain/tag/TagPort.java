package ruinMapper.hexagon.domain.tag;

/**
 * Something that marks a room, i.e. a shop, or a character
 * enables the user to use custom tags, to mark rooms with any
 * information they wish
 */
public interface TagPort {
    public String accessTag();

    public void changeTag(String newText);

    public void deleteTag();

}
