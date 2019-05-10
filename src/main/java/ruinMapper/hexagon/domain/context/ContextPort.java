package ruinMapper.hexagon.domain.context;

import ruinMapper.hexagon.domain.area.AreaPort;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.util.Set;

/**
 * A Context is the environment the maps and rooms apply to
 * i.e. a game like Metroid, or Hollow Knight.
 * It keeps track of everything that is interesting on its own
 */
public interface ContextPort {
    /**
     * Returns the name of the Context
     *
     * @return Context name
     */
    public String accessName();

    /**
     * Creates an Area with the specified title and returns it for convenience
     *
     * @param title Title of the new Area
     * @return The created Area
     */
    public AreaPort createArea(String title);

    /**
     * Deletes the Area from context.
     * Deletes every Room and by extension every Hint assigned to that area
     *
     * @param areaToDelete The area to delete
     */
    public void deleteArea(AreaPort areaToDelete);


    /**
     * Returns the Area with the specified title
     *
     * @param titleOfArea Title of the Area that should be returned
     * @return Area with the specified title
     */
    public AreaPort accessArea(String titleOfArea);

    /**
     * Returns all Areas that exist within the Context
     *
     * @return All existing Areas
     */
    public Set<AreaPort> accessEveryArea();

    /**
     * Creates a new valid Tag in the Context and returns it for convenience.
     * Rooms can only be tagged with valid Tags
     *
     * @param name Name of the Tag
     * @return The created Tag
     */
    public TagPort createValidTag(String name);

    /**
     * Deletes the specified valid Tag from the Context.
     * Removes the Tag from all Rooms tagged with it.
     *
     * @param tagToDelete
     */
    public void deleteValidTag(TagPort tagToDelete);

    /**
     * Returns all Tags that are valid in the Context.
     *
     * @return All valid Tags
     */
    public Set<TagPort> accessValidTags();

    /**
     * Creates a new Quest in with the specified title in the Context and returns it for convenience.
     *
     * @param title Title of the new Quest
     * @return The created Quest
     */
    public QuestPort createQuest(String title);

    /**
     * Deletes the specified Quest from the Context.
     * Removes the Quest from all Rooms that were associated with it.
     *
     * @param quest The Quest to delete
     */
    public void deleteQuest(QuestPort quest);

    /**
     * Returns all Quest in Context, also ones created in Rooms.
     *
     * @return All existing Quests
     */
    public Set<QuestPort> accessEveryQuest();

    /**
     * Returns all Hints that exist within the Context.
     *
     * @return All existing Hints
     */
    public Set<HintPort> accessEveryHint();

    /**
     * Deletes the current Context.
     * Results in the deletion of everything in the context
     */
    public void deleteContext();

}
