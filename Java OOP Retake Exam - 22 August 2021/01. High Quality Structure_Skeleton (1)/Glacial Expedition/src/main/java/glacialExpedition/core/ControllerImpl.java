package glacialExpedition.core;

import glacialExpedition.common.ConstantMessages;
import glacialExpedition.common.ExceptionMessages;
import glacialExpedition.models.explorers.AnimalExplorer;
import glacialExpedition.models.explorers.Explorer;
import glacialExpedition.models.explorers.GlacierExplorer;
import glacialExpedition.models.explorers.NaturalExplorer;
import glacialExpedition.models.mission.Mission;
import glacialExpedition.models.mission.MissionImpl;
import glacialExpedition.models.states.State;
import glacialExpedition.models.states.StateImpl;
import glacialExpedition.repositories.ExplorerRepository;
import glacialExpedition.repositories.StateRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private ExplorerRepository explorerRepository;
    private StateRepository stateRepository;
    private int countExploredStates;

    public ControllerImpl() {
        this.explorerRepository = new ExplorerRepository();
        this.stateRepository = new StateRepository();
    }

    @Override
    public String addExplorer(String type, String explorerName) {
        Explorer explorer;
        switch (type) {
            case "AnimalExplorer":
                explorer = new AnimalExplorer(explorerName);
                break;
            case "GlacierExplorer":
                explorer = new GlacierExplorer(explorerName);
                break;
            case "NaturalExplorer":
                explorer = new NaturalExplorer(explorerName);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.EXPLORER_INVALID_TYPE);
        }
        explorerRepository.add(explorer);
        return String.format(ConstantMessages.EXPLORER_ADDED
                , explorer.getClass().getSimpleName(), explorerName);
    }

    @Override
    public String addState(String stateName, String... exhibits) {
        State state = new StateImpl(stateName);
        for (String exhibit : exhibits) {
            state.getExhibits().add(exhibit);
        }
//        or
//        Collection<String> collectionExhibits = state.getExhibits();
//        Collections.addAll(collectionExhibits, exhibits);
        this.stateRepository.add(state);
        return String.format(ConstantMessages.STATE_ADDED, stateName);
    }

    @Override
    public String retireExplorer(String explorerName) {
        Explorer explorer = explorerRepository.byName(explorerName);
        if (explorer == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.EXPLORER_DOES_NOT_EXIST
                    , explorerName));
        }
        explorerRepository.remove(explorer);
        return String.format(ConstantMessages.EXPLORER_RETIRED, explorerName);
    }

    @Override
    public String exploreState(String stateName) {
        List<Explorer> collectExplorers = explorerRepository.getCollection().stream()
                .filter(explorer -> explorer.getEnergy() > 50)
                .collect(Collectors.toList());
        if (collectExplorers.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.STATE_EXPLORERS_DOES_NOT_EXISTS);
        }
        State stateToExplore = stateRepository.byName(stateName);
        Mission mission = new MissionImpl();
        mission.explore(stateToExplore, collectExplorers);
        countExploredStates++;
        long numberRetireExplorers = collectExplorers.stream()
                .filter(explorer -> explorer.getEnergy() == 0).count();
        return String.format(ConstantMessages.STATE_EXPLORER, stateName, numberRetireExplorers);
    }

    @Override
    public String finalResult() {
        StringBuilder stringBuilder = new StringBuilder(String.format(ConstantMessages.FINAL_STATE_EXPLORED
                , countExploredStates)).append(System.lineSeparator())
                .append(ConstantMessages.FINAL_EXPLORER_INFO);

        for (Explorer explorer : explorerRepository.getCollection()) {
            stringBuilder.append(System.lineSeparator())
                    .append(String.format(ConstantMessages.FINAL_EXPLORER_NAME, explorer.getName()))
                    .append(System.lineSeparator())
                    .append(String.format(ConstantMessages.FINAL_EXPLORER_ENERGY, explorer.getEnergy()))
                    .append(System.lineSeparator());

            Collection<String> exhibitsList = explorer.getSuitcase().getExhibits();
            if (exhibitsList.isEmpty()) {
                stringBuilder.append(String.format(ConstantMessages.FINAL_EXPLORER_SUITCASE_EXHIBITS
                        , "None"));
            } else {
                String exhibitsString = exhibitsList.stream()
                        .collect(Collectors.joining(ConstantMessages.FINAL_EXPLORER_SUITCASE_EXHIBITS_DELIMITER));
                stringBuilder.append(String.format(ConstantMessages.FINAL_EXPLORER_SUITCASE_EXHIBITS
                        , exhibitsString));
            }
        }
        return stringBuilder.toString();
    }
}
