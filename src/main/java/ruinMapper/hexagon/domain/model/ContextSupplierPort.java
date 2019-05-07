package ruinMapper.hexagon.domain.model;

import ruinMapper.hexagon.domain.context.ContextPort;

/**
 * Acts as the entry point for the Application side to the domain
 */
public interface ContextSupplierPort {
    /**
     * Creates a new Context with the specified name and returns it for convenience.
     * The name is a unique specifier within the domain.
     *
     * @param name Unique name of the Context
     * @return the created Context
     */
    public ContextPort createNewContext(String name);

    /**
     * Loads the Context with the specified name from the repository.
     *
     * @param name unique name of the Context
     * @return The specified Context
     */
    public ContextPort loadContextByName(String name);

}
