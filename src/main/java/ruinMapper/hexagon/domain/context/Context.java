package ruinMapper.hexagon.domain.context;

import ruinMapper.hexagon.ComponentFactory;
import ruinMapper.hexagon.domain.area.AreaPort;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Context implements ContextPort {

    private Set<AreaPort> areaSet;
    private Set<TagPort> possibleTagSet;
    private Set<QuestPort> questSet;
    private CRUDRepositoryPort<Context> contextRepository;
    private UUID contextID;

    public Context(
            Set<AreaPort> areaSet,
            Set<TagPort> possibleTagSet,
            Set<QuestPort> questSet,
            CRUDRepositoryPort<Context> contextRepository,
            UUID contextID) {
        this.areaSet = areaSet;
        this.possibleTagSet = possibleTagSet;
        this.questSet = questSet;
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
        possibleTagSet.add(tag);
        tag.addTaggable(this);
        saveState();
        return tag;
    }

    @Override
    public void deleteTag(TagPort tag) {
        if (possibleTagSet.remove(tag)) {
            tag.deleteTag();
            saveState();
        }
    }

    @Override
    public void addTag(TagPort port) {
        if (possibleTagSet.add(port)) {
            port.addTaggable(this);
            saveState();
        }
    }

    // Context manages all possible Tags, so removing one is
    // essentially deleting it from the context
    @Override
    public void removeTag(TagPort port) {
        deleteTag(port);
    }

    @Override
    public Set<TagPort> accessTags() {
        return new HashSet<>(possibleTagSet);
    }

    @Override
    public QuestPort createQuest(String title) {
        QuestPort quest = ComponentFactory
                .createQuest(title);
        questSet.add(quest);
        saveState();
        return quest;
    }

    @Override
    public void deleteQuest(QuestPort quest) {
        if (questSet.remove(quest)) {
            quest.deleteQuest();
            saveState();
        }
    }

    @Override
    public Set<QuestPort> accessQuests() {
        return new HashSet<>(questSet);
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
}
