package ruinMapper.fixtures.repository;

import ruinMapper.hexagon.domain.invariant.InvariantKeeper;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;

public class StateKeeperRepoDummy implements
        CRUDRepositoryPort<InvariantKeeper> {
    @Override
    public void create(InvariantKeeper object) {

    }

    @Override
    public InvariantKeeper read(String ID) {
        return null;
    }

    @Override
    public void update(InvariantKeeper object) {

    }

    @Override
    public void delete(String ID) {

    }
}
