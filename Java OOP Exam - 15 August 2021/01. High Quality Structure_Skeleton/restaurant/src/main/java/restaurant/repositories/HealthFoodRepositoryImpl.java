package restaurant.repositories;

import restaurant.entities.drinks.interfaces.Beverages;
import restaurant.entities.healthyFoods.interfaces.HealthyFood;
import restaurant.repositories.interfaces.HealthFoodRepository;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class HealthFoodRepositoryImpl implements HealthFoodRepository<HealthyFood> {
    private Map<String, HealthyFood> healthyFoodMap;

    public HealthFoodRepositoryImpl() {
        healthyFoodMap = new LinkedHashMap<>();
    }

    @Override
    public HealthyFood foodByName(String name) {
        return healthyFoodMap.get(name);
    }

    @Override
    public Collection<HealthyFood> getAllEntities() {
        return healthyFoodMap.values();
    }

    @Override
    public void add(HealthyFood entity) {
        healthyFoodMap.put(entity.getName(), entity);
    }
}
