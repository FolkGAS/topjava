package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.stream()
                .limit(24)
                .forEach(meal -> {
            meal.setUserId(AuthorizedUser.getId()); save(meal);
        });
        MealsUtil.MEALS.stream()
                .skip(24)
                .limit(24)
                .forEach(meal -> {
                    meal.setUserId(AuthorizedUser.getId() + 1); save(meal);
                });
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id, int userId) {
        if (repository.get(id).getUserId() == userId) {
            repository.remove(id);
        }
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        return meal == null ? null : meal.getUserId() != userId ? null : meal;
    }

    @Override
    public List<Meal> getFilteredByDate(int userId, LocalDate dateFrom, LocalDate dateTo) {
        if (dateFrom == null && dateTo == null){
            return getAll(userId);
        }
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId && DateTimeUtil.isBetween(meal.getDate(), dateFrom, dateTo))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

