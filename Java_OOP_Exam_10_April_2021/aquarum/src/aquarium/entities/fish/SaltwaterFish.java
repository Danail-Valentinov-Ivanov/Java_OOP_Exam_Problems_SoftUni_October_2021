package aquarium.entities.fish;

public class SaltwaterFish extends BaseFish {
    //    todo
    private static final int initialSize = 5;
    private static final int increaseSize = 2;

    public SaltwaterFish(String name, String species, double price) {
        super(name, species, price);
        setSize(initialSize);
    }

    @Override
    public void eat() {
        int newSize = getSize() + increaseSize;
        setSize(newSize);
    }
}
