package ru.javawebinar.topjava.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

@ContextConfiguration({"classpath:spring-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {
    public static final Integer ID = START_SEQ + 2;

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;
    @Autowired
    private InMemoryMealRepositoryImpl repository;

    @Before
    public void setUp() throws Exception {
        service.save(BREAKFAST, UserTestData.USER_ID);
        service.save(LUNCH, UserTestData.USER_ID);
        service.save(SUPPER, UserTestData.USER_ID);
    }

    @After
    public void tearDown() throws Exception {
        repository.clear();
        BREAKFAST.setId(null);
        LUNCH.setId(null);
        SUPPER.setId(null);
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(ID, UserTestData.USER_ID);
        MATCHER.assertEquals(BREAKFAST, meal);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        Meal meal = service.get(START_SEQ, UserTestData.USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testUnauthorizedUserGet() throws Exception {
        Meal meal = service.get(ID, UserTestData.ADMIN_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(ID, UserTestData.USER_ID);
        List<Meal> meals = Collections.unmodifiableList(service.getAll(UserTestData.USER_ID));
        MATCHER.assertCollectionEquals(Arrays.asList(SUPPER, LUNCH), meals);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(ID * 2, UserTestData.USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testUnauthorizedUserDelete() throws Exception {
        service.delete(ID, UserTestData.ADMIN_ID);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        List<Meal> meals = service.getBetweenDateTimes(BREAKFAST.getDateTime(), LUNCH.getDateTime(), UserTestData.USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(LUNCH, BREAKFAST), meals);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> meals = service.getAll(UserTestData.USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(SUPPER, LUNCH, BREAKFAST), meals);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(BREAKFAST);
        updated.setDescription("Updated");
        service.update(updated, UserTestData.USER_ID);
        MATCHER.assertEquals(updated, service.get(ID, UserTestData.USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testUnauthorizedUserUpdate() throws Exception {
        Meal updated = new Meal(BREAKFAST);
        updated.setDescription("Updated");
        service.update(updated, UserTestData.ADMIN_ID);
    }

    @Test
    public void save() throws Exception {
        Meal newMeal = new Meal(BREAKFAST);
        newMeal.setId(null);
        newMeal.setDescription("New meal");
        Meal created = service.save(newMeal, UserTestData.USER_ID);
        newMeal.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(SUPPER, LUNCH, newMeal, BREAKFAST), service.getAll(UserTestData.USER_ID));
    }
}