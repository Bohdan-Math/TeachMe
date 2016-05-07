package ua.teachme.web.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.teachme.ConnectDB;
import ua.teachme.util.exception.EntityNotFoundException;
import ua.teachme.util.user.UserUtil;

import static org.junit.Assert.*;

//tests with Spring
@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/connect-with-db.xml", "classpath:spring/work-with-db.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(ConnectDB.HSQLDB)
@Sql(scripts = "classpath:db/hsqldb/populateHSQLDB.sql") //execute before every test in this class
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    public void testUserController(){
        assertNotNull(userController);
    }

    @Test//(expected = EntityNotFoundException.class)
    public void testGetByEmail() throws Exception {
        userController.getByEmail("anonymous@gmail.com");
        //userController.getByEmail("");
    }

    @Test
    public void testGetAll() throws Exception {
        assertEquals(3, userController.getAll().size());
    }

    @Test
    public void testSave() throws Exception {
        assertEquals(3, userController.getAll().size());
        assertEquals(UserUtil.newUser, userController.save(UserUtil.newUser));
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