package ruinMapper.hexagon.domain.context;

import ruinMapper.hexagon.domain.Taggable;
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
public interface ContextPort extends Taggable {
    public AreaPort createArea(String title);

    public Set<AreaPort> accessAreas();

    public TagPort createTag(String name);

    public void deleteTag(TagPort tag);

    public QuestPort createQuest(String title);

    public void deleteQuest(QuestPort quest);

    public Set<QuestPort> accessQuests();

    public Set<HintPort> accessHints();

}
