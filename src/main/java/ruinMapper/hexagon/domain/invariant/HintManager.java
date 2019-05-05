package ruinMapper.hexagon.domain.invariant;

import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.model.ComponentManager;
import ruinMapper.hexagon.domain.model.HasHint;

import java.util.Set;

public interface HintManager extends ComponentManager {
    /**
     * Invariants:
     * 1. Add the hint to the HasHint object and if it is a Room
     * add it to the hint
     *
     * @param value the Hint to add
     * @param key   a Room
     */
    public void addHint(HintPort value, HasHint key);

    /**
     * Invariants:
     * 1. Remove the Hint from HasHint and if it is a Room
     * remove the room from the Hint
     *
     * @param value Hint to remove
     * @param key   a Room
     */
    public void removeHint(HintPort value,
                           HasHint key);

    /**
     * Returns the HasHints hints
     *
     * @param hasHint
     * @return
     */
    public Set<HintPort> accessHints(HasHint hasHint);
}
