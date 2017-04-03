package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Controller
public class MealRestController {
    private static final Logger LOG = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public Meal create() {
        return new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
    }

    public Meal save(HttpServletRequest request) {
        String id = request.getParameter("id");
        String dateTime = request.getParameter("dateTime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");

        Meal meal = new Meal(id.isEmpty() ? null :
                Integer.valueOf(id),
                LocalDateTime.parse(dateTime),
                description, Integer.valueOf(calories));
        if (meal.getUserId() == null) {
            meal.setUserId(AuthorizedUser.id());
        }

        LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        return service.save(meal);
    }

    public void delete(HttpServletRequest request) {
        int id = getId(request);
        LOG.info("Delete {}", id);
        service.delete(id, AuthorizedUser.id());
    }

    public Meal get(HttpServletRequest request) {
        return service.get(getId(request), AuthorizedUser.id());
    }

    public List<MealWithExceed> getAll() {
        return service.getAll(AuthorizedUser.id());
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}