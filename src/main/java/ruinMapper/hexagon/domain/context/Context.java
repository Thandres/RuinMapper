package ruinMapper.hexagon.domain.context;

import ruinMapper.hexagon.domain.ComponentSuper;
import ruinMapper.hexagon.domain.DomainInjector;
import ruinMapper.hexagon.domain.area.Area;
import ruinMapper.hexagon.domain.area.AreaPort;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.quest.Quest;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.tag.Tag;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Context extends ComponentSuper implements
        ContextPort {

    private String name;
    private Set<String> areas;
    private Set<String> validTags;
    private Set<String> quests;
    private Set<String> keywords;
    private CRUDRepositoryPort<Context> contextRepository;
    private CRUDRepositoryPort<Tag> tagRepository;
    private CRUDRepositoryPort<Quest> questRepository;
    private CRUDRepositoryPort<Area> areaRepository;


    public Context(
            String name,
            CRUDRepositoryPort<Context> contextRepository,
            CRUDRepositoryPort<Tag> tagRepository,
            CRUDRepositoryPort<Quest> questRepository,
            CRUDRepositoryPort<Area> areaRepository) {
        this.name = name;
        this.tagRepository = tagRepository;
        this.questRepository = questRepository;
        this.areaRepository = areaRepository;

        areas = new HashSet<>();
        validTags = new HashSet<>();
        quests = new HashSet<>();
        keywords = new HashSet<>();
        this.contextRepository = contextRepository;


    }

    @Override
    public String accessName() {
        return name;
    }

    @Override
    public AreaPort createArea(String title) {
        AreaPort newArea = DomainInjector
                .createArea(title);
        areas.add(newArea.toString());
        saveState();
        return newArea;
    }

    @Override
    public void deleteArea(AreaPort areaToDelete) {
        if (areas.remove(areaToDelete.toString())) {
            areaToDelete.deleteArea();
            saveState();
        }
    }

    @Override
    public AreaPort accessArea(String titleOfArea) {
        return areas.stream()
                .map(areaID -> areaRepository.read(areaID))
                .filter(area -> area.accessTitle()
                        .equals(titleOfArea)).findFirst()
                .orElseGet(null);//TODO error object
    }

    @Override
    public Set<AreaPort> accessEveryArea() {
        return areas.stream()
                .map(areaID -> areaRepository.read(areaID))
                .collect(Collectors.toSet());
    }

    @Override
    public TagPort createValidTag(String name) {
        TagPort tag = DomainInjector.createTag(name);
        validTags.add(tag.toString());
        saveState();
        return tag;
    }

    @Override
    public void deleteValidTag(TagPort tagToDelete) {
        if (validTags.remove(tagToDelete.toString())) {
            accessEveryArea()
                    .forEach(areaPort -> areaPort
                            .accessRooms()
                            .forEach(roomPort -> roomPort
                                    .removeTag(
                                            tagToDelete)));
            tagToDelete.deleteTag();
            saveState();
        } else if (keywords.remove(tagToDelete)) {
            accessEveryHint()
                    .forEach(hintPort -> hintPort
                            .removeKeyWord(tagToDelete));
            tagToDelete.deleteTag();
            saveState();
        }
    }

    @Override
    public Set<TagPort> accessValidTags() {
        return validTags.stream()
                .map(tagID -> tagRepository.read(tagID))
                .collect(Collectors.toSet());
    }

    @Override
    public TagPort createKeyword(String keyword) {
        TagPort keyTag = DomainInjector
                .createTag(keyword);
        keywords.add(keyTag.toString());
        saveState();
        return keyTag;
    }

    @Override
    public void deleteKeyword(TagPort keywordToDelete) {
        deleteValidTag(keywordToDelete);
    }

    @Override
    public Set<TagPort> accessEveryKeyWord() {
        return keywords.stream()
                .map(keywordID -> tagRepository
                        .read(keywordID))
                .collect(Collectors.toSet());
    }

    @Override
    public QuestPort createQuest(String title) {
        QuestPort quest = DomainInjector
                .createQuest(title);
        quests.add(quest.toString());
        saveState();
        return quest;
    }

    @Override
    public void deleteQuest(QuestPort quest) {
        if (quests.remove(quest.toString())) {
            quest.accessQuestRooms().forEach(
                    roomPort -> roomPort
                            .removeQuest(quest));
            saveState();
        }
    }

    @Override
    public Set<QuestPort> accessEveryQuest() {
        return quests.stream()
                .map(questID -> questRepository
                        .read(questID))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<HintPort> accessEveryHint() {
        return accessEveryArea().stream()
                .flatMap(
                        area -> area.accessHintsOnArea()
                                .stream())
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteContext() {
        accessEveryArea().forEach(AreaPort::deleteArea);
        areas.clear();
        accessEveryQuest().forEach(QuestPort::deleteQuest);
        quests.clear();

        accessEveryKeyWord().forEach(TagPort::deleteTag);
        keywords.clear();

        accessValidTags().forEach(TagPort::deleteTag);
        validTags.clear();

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

    public Set<String> getAreas() {
        return areas;
    }

    public void setAreas(
            Set<String> areas) {
        this.areas = areas;
    }

    public Set<String> getValidTags() {
        return validTags;
    }

    public void setValidTags(
            Set<String> validTags) {
        this.validTags = validTags;
    }

    public Set<String> getQuests() {
        return quests;
    }

    public void setQuests(
            Set<String> quests) {
        this.quests = quests;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(
            Set<String> keywords) {
        this.keywords = keywords;
    }
}
