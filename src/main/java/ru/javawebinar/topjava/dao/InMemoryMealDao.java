package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealDao implements MealDao {
    private AtomicInteger atomId = new AtomicInteger(0);
    private static ConcurrentHashMap<Integer, Meal> meals = new ConcurrentHashMap<>();

    public InMemoryMealDao() {
            addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
            addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
            addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
            addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
            addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
            addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
            addMeal(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 8, 0), "Завтрак", 1000));
            addMeal(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Обед", 800));
            addMeal(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Ужин", 200));
            addMeal(new Meal(LocalDateTime.of(2015, Month.JUNE, 2, 9, 0), "Завтрак", 1000));
            addMeal(new Meal(LocalDateTime.of(2015, Month.JUNE, 2, 15, 0), "Обед", 800));
            addMeal(new Meal(LocalDateTime.of(2015, Month.JUNE, 2, 22, 0), "Ужин", 500));
    }

    @Override
    public void addMeal(Meal meal) {
        int id = atomId.incrementAndGet();
        meal.setId(id);
        meals.put(id, meal);
    }

    @Override
    public void updateMeal(Meal meal) {
        meals.put(meal.getId(), meal);
    }

    @Override
    public void removeMeal(Meal meal) {
        meals.remove(meal.getId());
    }

    @Override
    public Meal getMealById(int id) {
        return meals.getOrDefault(id, new Meal(LocalDateTime.MIN, "", 0));
    }

    @Override
    public List<Meal> listMeals() {
        List<Meal> copy = new ArrayList<>(meals.values());
        copy.sort(Comparator.comparing(Meal::getDateTime));
        return copy;
    }
}
