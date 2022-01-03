package glacialExpedition.repositories;

import glacialExpedition.models.states.State;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class StateRepository implements Repository<State> {
    Map<String, State> statesMap;

    public StateRepository() {
        this.statesMap = new LinkedHashMap<>();
    }

    @Override
    public Collection<State> getCollection() {
        return Collections.unmodifiableCollection(statesMap.values());
    }

    @Override
    public void add(State entity) {
        statesMap.put(entity.getName(), entity);
    }

    @Override
    public boolean remove(State entity) {
        return statesMap.remove(entity.getName()) != null;
    }

    @Override
    public State byName(String name) {
        return statesMap.get(name);
    }
}
