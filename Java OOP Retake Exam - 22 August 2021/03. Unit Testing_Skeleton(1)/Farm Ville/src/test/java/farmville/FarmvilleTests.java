package farmville;

import org.junit.Assert;
import org.junit.Test;

public class FarmvilleTests {
    //TODO: TEST ALL THE FUNCTIONALITY OF THE PROVIDED CLASS Farm
    private String name = "Cow farm";
    private int capacity = 3;

    @Test
    public void testShouldCreateFarm() {
        Farm farm = new Farm(name, capacity);
        Assert.assertEquals(name, farm.getName());
        Assert.assertEquals(capacity, farm.getCapacity());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldThrowBecauseNameIsNull() {
        new Farm(null, 2);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldThrowBecauseNameIsWitheSpice() {
        new Farm(" ", 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorShouldThrowBecauseCapacityIsNegative() {
        new Farm(name, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddThrowBecauseFarmIsFull() {
        Farm farm = new Farm(name, 0);
        Animal animal = new Animal("Cow", 10);
        farm.add(animal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddShouldThrowBecauseAnimalExist() {
        Farm farm = new Farm(name, capacity);
        Animal animal1 = new Animal("Cow", 10);
        Animal animal2 = new Animal("Cow", 15);
        farm.add(animal1);
        farm.add(animal2);
    }

    @Test
    public void testAddMethodShouldAddAnimal() {
        Farm farm = new Farm(name, capacity);
        Animal animal = new Animal("Cow", 10);
        farm.add(animal);
        Assert.assertEquals(1, farm.getCount());
    }

    @Test
    public void testRemoveMethodShouldRemoveAnimal() {
        Farm farm = new Farm(name, capacity);
        Animal animal = new Animal("Cow", 10);
        farm.add(animal);
        Assert.assertEquals(1, farm.getCount());
        Assert.assertTrue(farm.remove("Cow"));
        Assert.assertEquals(0, farm.getCount());
    }

    @Test
    public void testRemoveMethodShouldNotRemoveAnimal() {
        Farm farm = new Farm(name, capacity);
        Animal animal = new Animal("Cow", 10);
        farm.add(animal);
        Assert.assertEquals(1, farm.getCount());
        Assert.assertFalse(farm.remove("Pig"));
        Assert.assertEquals(1, farm.getCount());
    }
}
