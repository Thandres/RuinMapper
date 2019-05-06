package ruinMapper.hexagon.domain.context;

import ruinMapper.hexagon.domain.area.AreaPort;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.invariant.AreaManager;
import ruinMapper.hexagon.domain.invariant.QuestManager;
import ruinMapper.hexagon.domain.invariant.TagManager;
import ruinMapper.hexagon.domain.model.*;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Context extends ComponentSuper implements
        ContextPort, HasTag,
        HasQuest, HasArea {

    private String name;
    private AreaManager areaManager;

    private TagManager tagManager;
    private QuestManager questManager;
    private String stateKeeperID;
    private CRUDRepositoryPort<Context> contextRepository;
    private UUID contextID;

    public Context(
            String name, AreaManager areaManager,
            TagManager tagManager,
            QuestManager questManager,
            String stateKeeperID,
            CRUDRepositoryPort<Context> contextRepository,
            UUID contextID) {
        this.name = name;
        this.areaManager = areaManager;
        this.tagManager = tagManager;
        this.questManager = questManager;
        this.stateKeeperID = stateKeeperID;

        this.contextRepository = contextRepository;
        this.contextID = contextID;
    }

    @Override
    public String accessName() {
        return name;
    }

    @Override
    public AreaPort createArea(String title) {
        AreaPort newArea = ComponentFactory
                .createArea(title);
        areaManager.addArea(newArea, this);
        saveState();
        return newArea;
    }

    @Override
    public Set<AreaPort> accessAreas() {
        return areaManager.accessAreas(this);
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
        return tagManager.accessTags(this);
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
        return areaManager.accessAreas(this).stream()
                .flatMap(
                        area -> area.accessHintsOnArea()
                                .stream())
                .collect(Collectors.toSet());
    }

    @Override
    public String getID() {
        return toString();
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

    public String getStateKeeperID() {
        return stateKeeperID;
    }
}
