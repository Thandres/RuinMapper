package ruinMapper.hexagon.application;


import ruinMapper.hexagon.domain.ContextSupplierPort;
import ruinMapper.hexagon.domain.DomainInjector;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.infrastructure.persistence.RepositoryInjector;


public class DomainAdapter {

    private String workingDirectoryPath;
    private ContextSupplierPort factory;

    public DomainAdapter(
            String workingDirectoryPath) {
        this.workingDirectoryPath = workingDirectoryPath;
    }


    public ContextPort createNewContext(String name) {
        preparePersistence();
        return factory.createNewContext(name);

    }


    public ContextPort loadContextByName(String name) {
        preparePersistence();
        return factory.loadContextByName(name);
    }

    private void preparePersistence() {
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
