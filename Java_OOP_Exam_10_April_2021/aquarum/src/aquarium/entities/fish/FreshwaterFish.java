package aquarium.entities.fish;

public class FreshwaterFish extends BaseFish{
//    todo
    private static final int initialSize = 3;
    public FreshwaterFish(String name, String species, double price) {
        super(name, species, price);
        setSize(initialSize);
    }

    @Override
    public void eat() {
        int newSize = getSize() + initialSize;
        setSize(newSize);
    }
}
