package ruinMapper.hexagon.domain.context;

import ruinMapper.hexagon.ComponentFactory;
import ruinMapper.hexagon.domain.ComponentSuper;
import ruinMapper.hexagon.domain.ComponentType;
import ruinMapper.hexagon.domain.HasQuest;
import ruinMapper.hexagon.domain.HasTag;
import ruinMapper.hexagon.domain.area.AreaPort;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.invariant.QuestManager;
import ruinMapper.hexagon.domain.invariant.TagManager;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Context extends ComponentSuper implements
        ContextPort, HasTag,
        HasQuest {

    private Set<AreaPort> areaSet;
    private TagManager tagManager;
    private QuestManager questManager;

    private CRUDRepositoryPort<Context> contextRepository;
    private UUID contextID;

    public Context(
            Set<AreaPort> areaSet,
            TagManager tagManager,

            QuestManager questManager,
            CRUDRepositoryPort<Context> contextRepository,
            UUID contextID) {
        this.areaSet = areaSet;
        this.tagManager = tagManager;
        this.questManager = questManager;

        this.contextRepository = contextRepository;
        this.contextID = contextID;
    }

    @Override
    public AreaPort createArea(String title) {
        AreaPort newArea = ComponentFactory
                .createArea(title);
        areaSet.add(newArea);
        saveState();
        return newArea;
    }

    @Override
    public Set<AreaPort> accessAreas() {
        return new HashSet<>(areaSet);
    }

    @Override
    public TagPort createTag(String name) {
        TagPort tag = ComponentFactory.createTag(name);
        tagManager.addTag(tag, this);
        saveState();
        return tag;
    }

    @Override
    public Set<TagPort> accessTags() {
        return new HashSet<>(tagManager.accessTags(this));
    }

    @Override
    public QuestPort createQuest(String title) {
        QuestPort quest = ComponentFactory
                .createQuest(title);
        questManager.addQuest(quest, this);
        saveState();
        return quest;
    }

    @Override
    public void deleteQuest(QuestPort quest) {
        questManager.removeQuest(quest, this);
        saveState();
    }

    @Override
    public Set<QuestPort> accessQuests() {
        return questManager.accessQuests(this);
    }

    @Override
    public Set<HintPort> accessHints() {
        return areaSet.stream().flatMap(
                area -> area.accessHintsOnArea().stream())
                .collect(Collectors.toSet());
    }

    @Override
    public ComponentType getType() {
        return ComponentType.CONTEXT;
    }

    @Override
    public void saveState() {
        contextRepository.update(this);
    }

    @Override
    public String toString() {
        return contextID.toString();
    }
}
