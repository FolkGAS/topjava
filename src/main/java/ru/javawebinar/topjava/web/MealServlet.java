package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.InMemoryMealDao;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(UserServlet.class);
    private static MealDao mealDao = new InMemoryMealDao();
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final int EXCEEDING_CALORIES = 2000;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("doGet");
        String edit = req.getParameter("edit");
        String remove = req.getParameter("remove");
        List<MealWithExceed> meals = MealsUtil.getFilteredWithExceeded(mealDao.listMeals(), LocalTime.MIN, LocalTime.MAX, EXCEEDING_CALORIES);
        req.setAttribute("meals", meals);

        if (edit != null) {
            int editId = Integer.parseInt(edit);
            Meal meal = mealDao.getMealById(editId);
            req.setAttribute("meal", meal);
        }
        if (remove != null && !remove.isEmpty()) {
            int remId = Integer.parseInt(remove);
            mealDao.removeMeal(mealDao.getMealById(remId));
            resp.sendRedirect("meals");
        } else req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("doPost");
        req.setCharacterEncoding("UTF-8");
        String userAgent = req.getHeader("User-Agent");
        String id = req.getParameter("ID");
        String dateTime = req.getParameter("DateTime");
        String description = req.getParameter("Description");
        String calories = req.getParameter("Calories");
        String from = req.getParameter("From");
        String to = req.getParameter("To");
        dateTime += ":00";

        if (userAgent != null && !userAgent.toLowerCase().contains("chrome")) {
            dateTime = dateTime.replace(' ', 'T');
        }

        if (!dateTime.startsWith(":00") && calories != null && !dateTime.isEmpty() && !calories.isEmpty()) {
            Meal meal = new Meal(
                    LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER), description,
                    Integer.parseInt(calories));
            if (id != null && !id.isEmpty() && !"0".equals(id)) {
                int intId = Integer.parseInt(id);
                meal.setId(intId);
                mealDao.updateMeal(meal);
            } else {
                mealDao.addMeal(meal);
            }
        }

        LocalTime start = LocalTime.MIN;
        LocalTime end = LocalTime.MAX;

        if (from != null && !from.isEmpty()) {
            start = LocalTime.parse(from, TIME_FORMATTER);
        }
        if (to != null && !to.isEmpty()) {
            end = LocalTime.parse(to, TIME_FORMATTER);
        }
        List<MealWithExceed> meals = MealsUtil.getFilteredWithExceeded(mealDao.listMeals(), start, end, 2000);
        req.setAttribute("meals", meals);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}
