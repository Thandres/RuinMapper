package ruinMapper.hexagon.application;

import ruinMapper.hexagon.domain.ComponentFactory;
import ruinMapper.hexagon.domain.ContextSupplierPort;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.infrastructure.persistence.RepositoryFactory;


public class DomainAdapter {

    private String workingDirectoryPath;
    private ContextSupplierPort factory;

    public DomainAdapter(
            String workingDirectoryPath) {
        this.workingDirectoryPath = workingDirectoryPath;
    }


    public ContextPort createNewContext(String name) {
        preparePersistence(name);
        return factory.createNewContext(name);

    }


    public ContextPort loadContextByName(String name) {
        preparePersistence(name);
        return factory.loadContextByName(name);
    }

    private void preparePersistence(String contextName) {
        RepositoryFactory.prepareRepositories(
                workingDirectoryPath);
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
