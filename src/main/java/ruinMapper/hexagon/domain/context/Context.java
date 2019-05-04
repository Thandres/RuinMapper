package ruinMapper.hexagon.domain.context;

import ruinMapper.hexagon.ComponentFactory;
import ruinMapper.hexagon.domain.QuestManager;
import ruinMapper.hexagon.domain.Questable;
import ruinMapper.hexagon.domain.TagManager;
import ruinMapper.hexagon.domain.Taggable;
import ruinMapper.hexagon.domain.area.AreaPort;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Context implements ContextPort, Taggable,
        Questable {

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


    private void saveState() {
        contextRepository.update(this);
    }


    @Override
    public boolean isContext() {
        return true;
    }
}
