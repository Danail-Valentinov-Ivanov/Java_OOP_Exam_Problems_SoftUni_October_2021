package spaceStation.core;

import spaceStation.common.ConstantMessages;
import spaceStation.common.ExceptionMessages;
import spaceStation.models.astronauts.Astronaut;
import spaceStation.models.astronauts.Biologist;
import spaceStation.models.astronauts.Geodesist;
import spaceStation.models.astronauts.Meteorologist;
import spaceStation.models.bags.Bag;
import spaceStation.models.mission.Mission;
import spaceStation.models.mission.MissionImpl;
import spaceStation.models.planets.Planet;
import spaceStation.models.planets.PlanetImpl;
import spaceStation.repositories.AstronautRepository;
import spaceStation.repositories.PlanetRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private AstronautRepository astronautRepository;
    private PlanetRepository planetRepository;
    private int countExploredPlanets;

    public ControllerImpl() {
        astronautRepository = new AstronautRepository();
        planetRepository = new PlanetRepository();
    }

    @Override
    public String addAstronaut(String type, String astronautName) {
        Astronaut astronaut;
        switch (type){
            case "Biologist":
                astronaut = new Biologist(astronautName);
                break;
            case "Geodesist":
                astronaut = new Geodesist(astronautName);
                break;
            case "Meteorologist":
                astronaut = new Meteorologist(astronautName);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.ASTRONAUT_INVALID_TYPE);
        }
        astronautRepository.add(astronaut);
        return String.format(ConstantMessages.ASTRONAUT_ADDED, type, astronautName);
    }

    @Override
    public String addPlanet(String planetName, String... items) {
        List<String> itemsList = Arrays.asList(items);
        Planet planet = new PlanetImpl(planetName);
        planet.getItems().addAll(itemsList);
        planetRepository.add(planet);
        return String.format(ConstantMessages.PLANET_ADDED, planetName);
    }

    @Override
    public String retireAstronaut(String astronautName) {
        if (astronautRepository.getModels().stream()
                .noneMatch(astronaut -> astronaut.getName().equals(astronautName))){
            throw new IllegalArgumentException(String.format(ExceptionMessages.ASTRONAUT_DOES_NOT_EXIST
                    , astronautName));
        }
        Astronaut astronautToRemove = astronautRepository.findByName(astronautName);
        astronautRepository.remove(astronautToRemove);
        return String.format(ConstantMessages.ASTRONAUT_RETIRED, astronautName);
    }

    @Override
    public String explorePlanet(String planetName) {
        List<Astronaut> astronautsSuitable = astronautRepository.getModels().stream()
                .filter(astronaut -> astronaut.getOxygen() > 60).collect(Collectors.toList());
        if (astronautsSuitable.isEmpty()){
            throw new IllegalArgumentException(ExceptionMessages.PLANET_ASTRONAUTS_DOES_NOT_EXISTS);
        }
        int countAstronautsBeforeMission = astronautsSuitable.size();
        Mission mission = new MissionImpl();
        Planet planet = planetRepository.findByName(planetName);
        mission.explore(planet, astronautsSuitable);
        countExploredPlanets++;
        int countAstronautsAfterMission = astronautsSuitable.size();
        return String.format(ConstantMessages.PLANET_EXPLORED, planetName
                , countAstronautsBeforeMission - countAstronautsAfterMission);
    }

    @Override
    public String report() {
        StringBuilder stringBuilder = new StringBuilder(String.format(ConstantMessages.REPORT_PLANET_EXPLORED
        , countExploredPlanets)).append(System.lineSeparator())
                .append(ConstantMessages.REPORT_ASTRONAUT_INFO);
        for (Astronaut astronaut : astronautRepository.getModels()) {
            stringBuilder.append(System.lineSeparator())
                    .append(String.format(ConstantMessages.REPORT_ASTRONAUT_NAME, astronaut.getName()))
                    .append(System.lineSeparator())
                    .append(String.format(ConstantMessages.REPORT_ASTRONAUT_OXYGEN, astronaut.getOxygen()))
                    .append(System.lineSeparator());
            Bag bag = astronaut.getBag();
            String itemsString;
            if (bag.getItems().isEmpty()){
                 itemsString = "none";
            } else {
                 itemsString = bag.getItems().stream()
                        .collect(Collectors.joining(ConstantMessages.REPORT_ASTRONAUT_BAG_ITEMS_DELIMITER));
            }
            stringBuilder.append(String.format(ConstantMessages.REPORT_ASTRONAUT_BAG_ITEMS, itemsString));
        }
        return stringBuilder.toString();
    }
}
