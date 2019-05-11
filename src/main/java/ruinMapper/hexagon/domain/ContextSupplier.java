package ruinMapper.hexagon.domain;

import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.infrastructure.persistence.RepositoryFactory;

import static ruinMapper.hexagon.domain.ComponentFactory.createContext;

public class ContextSupplier implements
        ContextSupplierPort {

    private String workingDirectoryPath;
    private ContextSupplierPort factory;

    public ContextSupplier(
            String workingDirectoryPath) {
        this.workingDirectoryPath = workingDirectoryPath;
    }

    @Override
    public ContextPort createNewContext(String name) {
        preparePersistence(name);
        return createContext(name);

    }

    @Override
    public ContextPort loadContextByName(String name) {
        preparePersistence(name);
        return factory.loadContextByName(name);
    }

    private void preparePersistence(String contextName) {
        RepositoryFactory.prepareRepositories(
                workingDirectoryPath + contextName);
        factory = new ComponentFactory(
                RepositoryFactory.getQuestRepository(),
                RepositoryFactory.getRoomRepository(),
                RepositoryFactory
                        .getHintRepository(),
                RepositoryFactory.getTagRepository(),
                RepositoryFactory.getAreaRepository(),
                RepositoryFactory
                        .getContextRepository());// makes the Factory inject the prepared Repositories
    }
}
