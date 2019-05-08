package ruinMapper.fixtures.repository;

import ruinMapper.hexagon.domain.quest.Quest;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;

public class QuestRepoDummy implements
        CRUDRepositoryPort<Quest> {
    @Override
    public void create(Quest object) {

    }

    @Override
    public Quest read(String ID) {
        return null;
    }

    @Override
    public void update(Quest object) {

    }

    @Override
    public void delete(String ID) {

    }
}
