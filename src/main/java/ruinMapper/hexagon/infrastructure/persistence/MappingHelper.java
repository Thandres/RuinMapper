package ruinMapper.hexagon.infrastructure.persistence;

import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;

import java.util.Set;
import java.util.stream.Collectors;

public class MappingHelper {
    public static <T> Set<String> toStringSet(
            Set<T> tSet) {
        return tSet.stream().map(Object::toString)
                .collect(Collectors.toSet());
    }

    public static <T extends D, D> Set<D> toDomainSet(
            Set<String> stringSet,
            CRUDRepositoryPort<T> repository) {
        return stringSet.stream()
                .map(repository::read)
                .collect(Collectors.toSet());

    }
}
