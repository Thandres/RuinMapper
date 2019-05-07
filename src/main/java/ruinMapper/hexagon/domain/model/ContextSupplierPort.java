package ruinMapper.hexagon.domain.model;

import ruinMapper.hexagon.domain.context.ContextPort;

/**
 * Acts as the entry point for the Application side to the domain
 */
public interface ContextSupplierPort {
    /**
     * Creates a new Context with the specified name and returns it for convenience.
     *
     * The name is a unique specifier because one user only needs one map for any Context.
     * For example Person A only creates one Map for Metroid and takes notes about secrets and hints
     * on it. Person A does not need two different Maps for Metroid so the name of the Context
     * being unique is fine
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
