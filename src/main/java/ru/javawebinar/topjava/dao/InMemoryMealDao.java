package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryMealDao implements MealDao {
    private static int id;
    private static List<Meal> meals = new ArrayList<>();
    static {
        meals.add(new Meal(LocalDateTime.MIN, "", 0));
    }

    @Override
    public void addMeal(Meal meal) {
        id++;
        meal.setId(id);
        meals.add(id, meal);
    }

    @Override
    public void updateMeal(Meal meal) {
        meals.set(meal.getId(), meal);
    }

    @Override
    public void removeMeal(Meal meal) {
        int id = meal.getId();
        meals.remove(id);
        meals.add(id, new Meal(LocalDateTime.MIN, "", 0));
    }

    @Override
    public Meal getMealById(int id) {
        Optional<Meal> oMeal = meals.stream().filter(meal -> id == meal.getId()).findFirst();
        if (oMeal.isPresent()){
            return oMeal.get();
        }
        return new Meal(LocalDateTime.MIN, "", 0);
    }

    @Override
    public List<Meal> listMeals() {
        List<Meal> copy = new ArrayList<>();
        copy.addAll(meals);
        copy.sort((o1, o2) -> o1.getDateTime().compareTo(o2.getDateTime()));
        return copy;
    }
}
