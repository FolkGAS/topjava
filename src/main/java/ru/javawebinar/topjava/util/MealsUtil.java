package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.dao.InMemoryMealDao;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class MealsUtil {
    private static MealDao mealDao = new InMemoryMealDao();
    static {
        mealDao.addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        mealDao.addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        mealDao.addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        mealDao.addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealDao.addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        mealDao.addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        mealDao.addMeal(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 8, 0), "Завтрак", 1000));
        mealDao.addMeal(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Обед", 800));
        mealDao.addMeal(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Ужин", 200));
        mealDao.addMeal(new Meal(LocalDateTime.of(2015, Month.JUNE, 2, 9, 0), "Завтрак", 1000));
        mealDao.addMeal(new Meal(LocalDateTime.of(2015, Month.JUNE, 2, 15, 0), "Обед", 800));
        mealDao.addMeal(new Meal(LocalDateTime.of(2015, Month.JUNE, 2, 22, 0), "Ужин", 500));
    }

    public static List<MealWithExceed> mealsWithExceed(){
        return getFilteredWithExceeded(mealDao.listMeals(), LocalTime.MIN, LocalTime.MAX, 2000);
    }

    public static void main(String[] args) {
        List<MealWithExceed> mealsWithExceeded = getFilteredWithExceeded(mealDao.listMeals(), LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsWithExceeded.forEach(System.out::println);

        System.out.println(getFilteredWithExceededByCycle(mealDao.listMeals(), LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<MealWithExceed> getFilteredWithExceeded(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<MealWithExceed> getFilteredWithExceededByCycle(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        meals.forEach(meal -> caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        final List<MealWithExceed> mealsWithExceeded = new ArrayList<>();
        meals.forEach(meal -> {
            if (TimeUtil.isBetween(meal.getTime(), startTime, endTime)) {
                mealsWithExceeded.add(createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay));
            }
        });
        return mealsWithExceeded;
    }

    public static MealWithExceed createWithExceed(Meal meal, boolean exceeded) {
        MealWithExceed mealWithExceed = new MealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded);
        mealWithExceed.setId(meal.getId());
        return mealWithExceed;
    }
}