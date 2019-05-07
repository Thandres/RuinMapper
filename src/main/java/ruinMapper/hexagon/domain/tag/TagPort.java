package ruinMapper.hexagon.domain.tag;

/**
 * Something that marks a room, i.e. a shop, or a character
 * enables the user to use custom tags, to mark rooms with any
 * information they wish
 */
public interface TagPort {
    /**
     * Returns the name of the Tag
     *
     * @return Name of the Tag
     */
    public String accessTag();

    /**
     * Changes the name of the Tag to the new Text
     * @param newText
     */
    public void changeTag(String newText);

    /**
     * Deletes the Tag from the current Context.
     * Removes the Tag from all Rooms that were tagged with it.
     */
    public void deleteTag();

}
