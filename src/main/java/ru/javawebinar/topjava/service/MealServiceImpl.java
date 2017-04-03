package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        if (get(id, userId) == null) {
            throw new NotFoundException("Not authorized user meal. ID: " + id + ", UserID: " + userId);
        } else {
            repository.delete(id, userId);
        }
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        Meal meal = repository.get(id, userId);
        if (meal == null){
            throw new NotFoundException("Not authorized user meal. ID: " + id + ", UserID: " + userId);
        } else {
            return meal;
        }
    }

    @Override
    public List<MealWithExceed> getFiltered(int userId, String sDateFrom, String sDateTo, String sTimeFrom, String sTimeTo) {
        LocalDate dateFrom = sDateFrom.equals("") ? LocalDate.MIN : LocalDate.parse(sDateFrom, DateTimeUtil.DATE_FORMATTER);
        LocalDate dateTo = sDateTo.equals("") ? LocalDate.MAX : LocalDate.parse(sDateTo, DateTimeUtil.DATE_FORMATTER);
        LocalTime timeFrom = sTimeFrom.equals("") ? LocalTime.MIN : LocalTime.parse(sTimeFrom, DateTimeUtil.TIME_FORMATTER);
        LocalTime timeTo = sTimeTo.equals("") ? LocalTime.MAX : LocalTime.parse(sTimeTo, DateTimeUtil.TIME_FORMATTER);
        List<Meal> filtered = repository.getFilteredByDate(userId, dateFrom, dateTo);
        return MealsUtil.getFilteredWithExceeded(filtered, timeFrom, timeTo, AuthorizedUser.getCaloriesPerDay());
    }

    @Override
    public List<MealWithExceed> getAll(int userId) throws NotFoundException {
        List<Meal> meals = repository.getAll(userId);
        return MealsUtil.getWithExceeded(meals, AuthorizedUser.getCaloriesPerDay());
    }
}