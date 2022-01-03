package glacialExpedition.models.mission;

import glacialExpedition.models.explorers.Explorer;
import glacialExpedition.models.states.State;

import java.util.Collection;

public class MissionImpl implements Mission {
    @Override
    public void explore(State state, Collection<Explorer> explorers) {
        Collection<String> exhibitsCollection = state.getExhibits();
        for (Explorer explorer : explorers) {
            if (explorer.canSearch() && exhibitsCollection.iterator().hasNext()) {
                String currentExhibit = exhibitsCollection.iterator().next();
                explorer.getSuitcase().getExhibits().add(currentExhibit);
                exhibitsCollection.remove(currentExhibit);
                explorer.search();
            }
        }
    }
}
