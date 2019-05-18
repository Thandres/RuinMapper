package ruinMapper.hexagon.domain;

import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.infrastructure.persistence.RepositoryInjector;

import static ruinMapper.hexagon.domain.DomainInjector.createContext;

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
        RepositoryInjector.prepareRepositories(
                workingDirectoryPath);
        factory = new DomainInjector(
                RepositoryInjector.getQuestRepository(),
                RepositoryInjector.getRoomRepository(),
                RepositoryInjector
                        .getHintRepository(),
                RepositoryInjector.getTagRepository(),
                RepositoryInjector.getAreaRepository(),
                RepositoryInjector
                        .getContextRepository());// makes the Factory inject the prepared Repositories
    }
}
