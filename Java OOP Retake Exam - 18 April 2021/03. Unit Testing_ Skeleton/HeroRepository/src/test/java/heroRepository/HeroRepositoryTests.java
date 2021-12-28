package heroRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

public class HeroRepositoryTests {
    //TODO: TEST ALL THE FUNCTIONALITY OF THE PROVIDED CLASS HeroRepository
    private HeroRepository heroRepository;
    private Hero hero;
    private static final String NAME_HERO = "Dido";
    private static final int LEVEL_HERO = 5;

    @Before
    public void setUp() throws Exception {
        this.heroRepository = new HeroRepository();
        this.hero = new Hero(NAME_HERO, LEVEL_HERO);
    }

    @Test(expected = NullPointerException.class)
    public void createMethodShouldThrowForNullHero() {
        heroRepository.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createMethodShouldThrowForCreateExistHero() {
        heroRepository.create(hero);
        heroRepository.create(hero);
    }

    @Test
    public void createMethodShouldCreateHero() {
        Assert.assertEquals(0, heroRepository.getCount());
        heroRepository.create(hero);
        Assert.assertEquals(1, heroRepository.getCount());
        Hero createdHero = heroRepository.getHero("Dido");
        Assert.assertEquals(NAME_HERO, createdHero.getName());
        Assert.assertEquals(LEVEL_HERO, createdHero.getLevel());
    }

    @Test(expected = NullPointerException.class)
    public void removeMethodShouldTrowForNullName() {
        heroRepository.create(hero);
        heroRepository.remove(null);
    }

    @Test(expected = NullPointerException.class)
    public void removeMethodShouldTrowForWhiteSpaceName() {
        heroRepository.create(hero);
        heroRepository.remove("  ");
    }

    @Test
    public void removeMethodShouldRemoveHero() {
        heroRepository.create(hero);
        boolean isRemoved = heroRepository.remove("Dido");
        Assert.assertTrue(isRemoved);
    }

    @Test
    public void testGetHeroWithHighestLevelMethod() {
        heroRepository.create(hero);
        Hero hero2 = new Hero("Petia", 10);
        heroRepository.create(hero2);
        Hero highestLevelHero = heroRepository.getHeroWithHighestLevel();
        Assert.assertEquals("Petia", highestLevelHero.getName());
        Assert.assertEquals(10, highestLevelHero.getLevel());
    }

    @Test
    public void testGetHeroesMethod() {
        heroRepository.create(hero);
        Hero hero2 = new Hero("Petia", 10);
        heroRepository.create(hero2);
        Collection<Hero> returnedGetHeroesMethod = heroRepository.getHeroes();
        StringBuilder stringBuilder = new StringBuilder();
        for (Hero hero1 : returnedGetHeroesMethod) {
            stringBuilder.append(hero1.getName()).append(System.lineSeparator())
                    .append(hero1.getLevel()).append(System.lineSeparator());
        }
        String actual = stringBuilder.toString().trim();
        String expected = "Dido" + System.lineSeparator() + "5" + System.lineSeparator()
        + "Petia" + System.lineSeparator() + "10";
        Assert.assertEquals(expected, actual);
    }
}
