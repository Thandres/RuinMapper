package ruinMapper.hexagon.domain.context;

import ruinMapper.hexagon.domain.ComponentFactory;
import ruinMapper.hexagon.domain.ComponentSuper;
import ruinMapper.hexagon.domain.area.AreaPort;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Context extends ComponentSuper implements
        ContextPort {

    private String name;
    private Set<AreaPort> areas;
    private Set<TagPort> validTags;
    private Set<QuestPort> quests;
    private CRUDRepositoryPort<Context> contextRepository;


    public Context(
            String name,
            CRUDRepositoryPort<Context> contextRepository) {
        this.name = name;
        areas = new HashSet<>();
        validTags = new HashSet<>();
        quests = new HashSet<>();

        this.contextRepository = contextRepository;


        createArea("New Area");
    }

    @Override
    public String accessName() {
        return name;
    }

    @Override
    public AreaPort createArea(String title) {
        AreaPort newArea = ComponentFactory
                .createArea(title);
        areas.add(newArea);
        saveState();
        return newArea;
    }

    @Override
    public void deleteArea(AreaPort areaToDelete) {
        if (areas.remove(areaToDelete)) {
            areaToDelete.deleteArea();
            saveState();
        }
    }

    @Override
    public AreaPort accessArea(String titleOfArea) {
        return areas.stream()
                .filter(areaPort -> areaPort.accessTitle()
                        .equals(titleOfArea)).findFirst()
                .orElseGet(null);//TODO error object
    }

    @Override
    public Set<AreaPort> accessEveryArea() {
        return new HashSet<>(areas);
    }

    @Override
    public TagPort createValidTag(String name) {
        TagPort tag = ComponentFactory.createTag(name);
        validTags.add(tag);
        saveState();
        return tag;
    }

    @Override
    public void deleteValidTag(TagPort tagToDelete) {
        if (validTags.remove(tagToDelete)) {
            areas
                    .forEach(areaPort -> areaPort
                            .accessRooms()
                            .forEach(roomPort -> roomPort
                                    .removeTag(
                                            tagToDelete)));
            tagToDelete.deleteTag();
            saveState();
        }
    }

    @Override
    public Set<TagPort> accessValidTags() {
        return new HashSet<>(validTags);
    }

    @Override
    public QuestPort createQuest(String title) {
        QuestPort quest = ComponentFactory
                .createQuest(title);
        quests.add(quest);
        saveState();
        return quest;
    }

    @Override
    public void deleteQuest(QuestPort quest) {
        if (quests.remove(quest)) {
            quest.accessQuestRooms().forEach(
                    roomPort -> roomPort
                            .removeQuest(quest));
            saveState();
        }
    }

    @Override
    public Set<QuestPort> accessEveryQuest() {
        return new HashSet<>(quests);
    }

    @Override
    public Set<HintPort> accessEveryHint() {
        return areas.stream()
                .flatMap(
                        area -> area.accessHintsOnArea()
                                .stream())
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteContext() {
        Set<AreaPort> tempA = new HashSet<>(areas);
        areas.clear();
        tempA.forEach(AreaPort::deleteArea);
        Set<QuestPort> tempQ = new HashSet<>(quests);
        quests.clear();
        tempQ.forEach(QuestPort::deleteQuest);
        Set<TagPort> tempT = validTags;
        validTags.clear();
        tempT.forEach(TagPort::deleteTag);
        contextRepository.delete(toString());
    }

    @Override
    public void saveState() {
        contextRepository.update(this);
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AreaPort> getAreas() {
        return areas;
    }

    public void setAreas(
            Set<AreaPort> areas) {
        this.areas = areas;
    }

    public Set<TagPort> getValidTags() {
        return validTags;
    }

    public void setValidTags(
            Set<TagPort> validTags) {
        this.validTags = validTags;
    }

    public Set<QuestPort> getQuests() {
        return quests;
    }

    public void setQuests(
            Set<QuestPort> quests) {
        this.quests = quests;
    }
}
