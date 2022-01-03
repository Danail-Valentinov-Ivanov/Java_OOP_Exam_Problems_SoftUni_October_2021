package glacialExpedition.repositories;

import glacialExpedition.models.explorers.Explorer;

import java.util.*;

public class ExplorerRepository implements Repository<Explorer> {
    Map<String, Explorer> explorersMap;

    public ExplorerRepository() {
        this.explorersMap = new LinkedHashMap<>();
    }

    @Override
    public Collection<Explorer> getCollection() {
        return Collections.unmodifiableCollection(explorersMap.values());
    }

    @Override
    public void add(Explorer entity) {
        explorersMap.put(entity.getName(), entity);
    }

    @Override
    public boolean remove(Explorer entity) {
        return explorersMap.remove(entity.getName()) != null;
    }

    @Override
    public Explorer byName(String name) {
        return explorersMap.get(name);
    }
}
