package ruinMapper.fixtures;

import ruinMapper.hexagon.domain.QuestAndRoomStateKeeper;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;

public class StateKeeperRepoDummy implements
        CRUDRepositoryPort<QuestAndRoomStateKeeper> {
    @Override
    public void create(QuestAndRoomStateKeeper object) {

    }

    @Override
    public QuestAndRoomStateKeeper read(String ID) {
        return null;
    }

    @Override
    public void update(QuestAndRoomStateKeeper object) {

    }

    @Override
    public void delete(String ID) {

    }
}
