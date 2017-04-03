package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository {

    Meal save(Meal meal);

    void delete(int id, int userId);

    Meal get(int id, int userId);

    List<Meal> getFilteredByDate(int userId, LocalDate dateFrom, LocalDate dateTo);

    List<Meal> getAll(int userId);
}
