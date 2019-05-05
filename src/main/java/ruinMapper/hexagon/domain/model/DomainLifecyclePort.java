package ruinMapper.hexagon.domain.model;

import ruinMapper.hexagon.domain.context.ContextPort;

/**
 * Acts as the entry point for the Application side to the domain
 */
public interface DomainLifecyclePort {
    public ContextPort createNewContext(String name);

    public void deleteContext(ContextPort contextPort);

    public ContextPort loadContextById(String id);

}
