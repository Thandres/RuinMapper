package ruinMapper.hexagon.domain.hint;

import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;

public interface HintRepository extends
        CRUDRepositoryPort<Hint> {
    public Hint getImplementation(HintPort port);
}
