package ruinMapper.hexagon;

import ruinMapper.hexagon.domain.quest.Quest;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;

public class DomainInjectionHelper {
    public static CRUDRepositoryPort<Quest> questRepository;

    public static CRUDRepositoryPort<Quest> getQuestRepository() {
        return questRepository;
    }

    public static void setQuestRepository(
            CRUDRepositoryPort<Quest> repository) {
        questRepository = repository;
    }
}
