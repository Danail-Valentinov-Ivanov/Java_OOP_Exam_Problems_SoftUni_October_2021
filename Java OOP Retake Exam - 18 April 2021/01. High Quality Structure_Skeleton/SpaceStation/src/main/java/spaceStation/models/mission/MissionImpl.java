package spaceStation.models.mission;

import spaceStation.models.astronauts.Astronaut;
import spaceStation.models.planets.Planet;

import java.util.Collection;
import java.util.List;

public class MissionImpl implements Mission{

    @Override
    public void explore(Planet planet, List<Astronaut> astronauts) {
        for (int index = 0; index < astronauts.size(); index++) {
            Astronaut currentAstronaut = astronauts.get(index);
            for (int item = 0; item < planet.getItems().size(); item++) {
                String currentItem = planet.getItems().get(item);
                if (currentAstronaut.canBreath()){
                    currentAstronaut.getBag().getItems().add(currentItem);
                    planet.getItems().remove(currentItem);
                    currentAstronaut.breath();
                    item--;
                } else {
                    astronauts.remove(currentAstronaut);
                    index--;
                    break;
                }
            }
        }
    }
}