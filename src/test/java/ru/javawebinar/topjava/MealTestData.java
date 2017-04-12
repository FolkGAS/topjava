package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

public class MealTestData {

    public static final Meal BREAKFAST = new Meal(LocalDateTime.of(2017, Month.MARCH, 1, 10, 00), "Завтрак", 500);
    public static final Meal LUNCH = new Meal(LocalDateTime.of(2017, Month.MARCH, 1, 13, 30), "Обед", 1000);
    public static final Meal SUPPER = new Meal(LocalDateTime.of(2017, Month.MARCH, 1, 20, 00), "Ужин", 500);


    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>(
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getDateTime(), actual.getDateTime())
                            && Objects.equals(expected.getDescription(), actual.getDescription())
                            && Objects.equals(expected.getCalories(), actual.getCalories())
                    )
    );

}
