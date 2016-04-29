package ua.teachme.web.user;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.teachme.util.PopulatorDB;
import ua.teachme.util.exception.EntityNotFoundException;
import ua.teachme.util.user.UserUtil;

import static org.junit.Assert.*;

//tests with JUnit
public class UserControllerTest {

    private static ConfigurableApplicationContext appCtx;
    private static UserController userController;
    private static PopulatorDB populatorDB;

    @BeforeClass
    public static void beforeClass(){
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml");
        userController = appCtx.getBean(UserController.class);
        populatorDB = appCtx.getBean(PopulatorDB.class);
    }

    @Before//execute before every test in this class
    public void before(){
        populatorDB.execute();
    }

    @AfterClass
    public static void afterClass(){
        appCtx.close();
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetByEmail() throws Exception {
        userController.getByEmail("anonymous@gmail.com");
        userController.getByEmail("");
    }

    @Test
    public void testGetAll() throws Exception {
        assertEquals(3, userController.getAll().size());
    }

    @Test
    public void testSave() throws Exception {
        assertEquals(3, userController.getAll().size());
        userController.save(UserUtil.newUser);
        assertEquals(4, userController.getAll().size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGet() throws Exception {
        userController.get(1000001);
        userController.get(-1000001);
    }

    @Test
    public void testDelete() throws Exception {
        assertEquals(3, userController.getAll().size());
        userController.delete(1000001);
        assertEquals(2, userController.getAll().size());
    }
}