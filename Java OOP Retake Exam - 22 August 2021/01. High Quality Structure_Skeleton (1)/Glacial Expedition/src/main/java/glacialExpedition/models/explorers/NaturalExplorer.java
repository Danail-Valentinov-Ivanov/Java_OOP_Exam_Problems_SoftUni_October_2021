package glacialExpedition.models.explorers;

public class NaturalExplorer extends BaseExplorer {
    private static final double INITIAL_ENERGY = 60;

    public NaturalExplorer(String name) {
        super(name, INITIAL_ENERGY);
    }

    @Override
    public void search() {
        if (getEnergy() <= 7) {
            setEnergy(0);
        } else {
            setEnergy(getEnergy() - 7);
        }
    }
}
