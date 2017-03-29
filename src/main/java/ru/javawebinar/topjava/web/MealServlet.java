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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(UserServlet.class);
    private static MealDao mealDao = new InMemoryMealDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String edit = req.getParameter("edit");
        String remove = req.getParameter("remove");
        if (remove != null && !remove.isEmpty()) {
            int remId = Integer.parseInt(remove);
            mealDao.removeMeal(mealDao.getMealById(remId));
        }
        if (edit != null) {
            int editId = Integer.parseInt(edit);
            Meal meal = mealDao.getMealById(editId);
            req.setAttribute("meal", meal);
        }
        LOG.debug("doGet");
        List<MealWithExceed> meals = MealsUtil.mealsWithExceed();
        req.setAttribute("meals", meals);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String date = req.getParameter("Date");
        String time = req.getParameter("Time");
        String description = req.getParameter("Description");
        String calories = req.getParameter("Calories");
        if (date != null && time != null && description != null && calories != null
                && !date.isEmpty() && !time.isEmpty() && !description.isEmpty() && !calories.isEmpty()) {
            Meal meal = new Meal(
                    LocalDateTime.of(LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE),
                            LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))),
                    description,
                    Integer.parseInt(calories));
            String id = req.getParameter("ID");
            if (id != null && !id.isEmpty() && !"0".equals(id)) {
                int intId = Integer.parseInt(id);
                meal.setId(intId);
                mealDao.updateMeal(meal);
            } else {
                mealDao.addMeal(meal);
            }

        }
        String from = req.getParameter("From");
        String to = req.getParameter("To");

        LocalTime start = LocalTime.MIN;
        LocalTime end = LocalTime.MAX;
        if (from != null && !from.isEmpty()){
            start = LocalTime.parse(from, DateTimeFormatter.ofPattern("HH:mm"));
        }
        if (to != null && !to.isEmpty()){
            end = LocalTime.parse(to, DateTimeFormatter.ofPattern("HH:mm"));
        }
        List<MealWithExceed> meals = MealsUtil.getFilteredWithExceeded(mealDao.listMeals(), start, end, 2000);
        req.setAttribute("meals", meals);
        LOG.debug("doPost");
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
//        resp.sendRedirect("meals");
    }
}
