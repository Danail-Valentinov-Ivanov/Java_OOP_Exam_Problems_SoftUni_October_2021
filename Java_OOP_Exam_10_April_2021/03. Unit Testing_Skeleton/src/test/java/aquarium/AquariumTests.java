package aquarium;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AquariumTests {
    //TODO: TEST ALL THE FUNCTIONALITY OF THE PROVIDED CLASS Aquarium
    private static final String NAME = "freshAquarium";
    private static final String FISH_NAME = "freshFish";
    private static final int CAPACITY = 5;
    private Aquarium aquarium;

    @Before
    public void setUp() throws Exception {
         aquarium = new Aquarium(NAME, CAPACITY);
    }

    @Test
    public void constructorShouldCreateAquarium() {
        Assert.assertEquals(NAME, aquarium.getName());
        Assert.assertEquals(CAPACITY, aquarium.getCapacity());
    }

    @Test(expected = NullPointerException.class)
    public void constructorShouldThrowForNullValueForName() {
        new Aquarium(null, CAPACITY);
    }

    @Test(expected = NullPointerException.class)
    public void constructorShouldThrowForWhiteSpaceForName() {
        new Aquarium("  ", CAPACITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorShouldThrowForNegativeCapacity() {
        new Aquarium(NAME, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addMethodShouldThrowForFullAquarium() {
        Aquarium aquarium = new Aquarium(NAME, 1);
        Fish fish = new Fish(FISH_NAME);
        aquarium.add(fish);
        aquarium.add(fish);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeMethodShouldThrowForNotExistName() {
        Aquarium aquarium = new Aquarium(NAME, 1);
        Fish fish = new Fish(FISH_NAME);
        aquarium.add(fish);
        aquarium.remove("Fish");
    }

    @Test
    public void removeMethodShouldRemoveFish() {
        Fish fish = new Fish(FISH_NAME);
        aquarium.add(fish);
        aquarium.remove(FISH_NAME);
        Assert.assertEquals(5, aquarium.getCapacity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void sellFishMethodThrowForNotExistName() {
        Aquarium aquarium = new Aquarium(NAME, 1);
        Fish fish = new Fish(FISH_NAME);
        aquarium.add(fish);
        aquarium.sellFish("Fish");
    }

    @Test
    public void sellFishMethodShouldReturnFish() {
        Fish fish = new Fish(FISH_NAME);
        aquarium.add(fish);
        Fish sellFish = aquarium.sellFish(FISH_NAME);
        Assert.assertEquals(fish, sellFish);
        Assert.assertFalse(fish.isAvailable());
    }

    @Test
    public void testReportMethod() {
        Fish fish = new Fish(FISH_NAME);
        aquarium.add(fish);
        Fish fish2 = new Fish("Fish2");
        aquarium.add(fish2);
        String expectedOutput = "Fish available at freshAquarium: freshFish, Fish2";
        String actualOutput = aquarium.report();
        Assert.assertEquals(expectedOutput, actualOutput);
    }
}

