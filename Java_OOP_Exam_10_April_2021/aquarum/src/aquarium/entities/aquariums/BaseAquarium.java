package aquarium.entities.aquariums;

import aquarium.common.ConstantMessages;
import aquarium.common.ExceptionMessages;
import aquarium.entities.decorations.Decoration;
import aquarium.entities.fish.Fish;

import java.util.ArrayList;
import java.util.Collection;

public abstract class BaseAquarium implements Aquarium{
    private String name;
    private int capacity;
    private Collection<Decoration> decorations;
    private Collection<Fish> fish;

    public BaseAquarium(String name, int capacity) {
        setName(name);
        this.capacity = capacity;
        this.decorations = new ArrayList<>();
        this.fish = new ArrayList<>();
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()){
            throw new NullPointerException(ExceptionMessages.AQUARIUM_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    @Override
    public int calculateComfort() {
        return decorations.stream().mapToInt(decoration -> decoration.getComfort()).sum();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addFish(Fish fish) {
        if (capacity <= this.fish.size()){
            throw new IllegalStateException(ConstantMessages.NOT_ENOUGH_CAPACITY);
        }
        String fishWaterType = fish.getClass().getSimpleName().replace("Fish", "");
        if (!this.getClass().getSimpleName().contains(fishWaterType)){
            throw new IllegalStateException(ConstantMessages.WATER_NOT_SUITABLE);
        }
        this.fish.add(fish);
    }

    @Override
    public void removeFish(Fish fish) {
        this.fish.remove(fish);
    }

    @Override
    public void addDecoration(Decoration decoration) {
        this.decorations.add(decoration);
    }

    @Override
    public void feed() {
        for (Fish eachFish : fish) {
            eachFish.eat();
        }
    }
//{aquariumName} ({aquariumType}):
//Fish: {fishName1} {fishName2} {fishName3} (…) / Fish: none
//Decorations: {decorationsCount}
//Comfort: {aquariumComfort}
    @Override
    public String getInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%s (%s):", getName(), getClass().getSimpleName()))
                .append(System.lineSeparator());
        if (fish.isEmpty()){
            stringBuilder.append("none");
        } else {
            stringBuilder.append("Fish: ");
            for (Fish fish1 : fish) {
                stringBuilder.append(String.format("%s ", fish1.getName()));
            }
        }
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(String.format("Decorations: %d", this.decorations.size()))
                .append(System.lineSeparator())
                .append(String.format("Comfort: %d", calculateComfort()));
        return stringBuilder.toString();
    }

    @Override
    public Collection<Fish> getFish() {
        return this.fish;
    }

    @Override
    public Collection<Decoration> getDecorations() {
        return this.decorations;
    }
}
