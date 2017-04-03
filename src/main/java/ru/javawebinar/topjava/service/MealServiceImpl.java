package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

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
    public List<MealWithExceed> getAll(int userId) throws NotFoundException {
        List<Meal> meals = repository.getAll(userId);
        return MealsUtil.getWithExceeded(meals, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
}