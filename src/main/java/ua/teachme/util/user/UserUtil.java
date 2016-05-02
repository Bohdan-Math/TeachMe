package ua.teachme.util.user;

import ua.teachme.model.Role;
import ua.teachme.model.User;
import ua.teachme.util.notation.NotationUtil;

import java.util.Arrays;
import java.util.List;


public class UserUtil {

    public static List<User> users = Arrays.asList(
            new User(null, "admin", "password", "admin@someemail.com", NotationUtil.hours, NotationUtil.notations, Role.ADMIN),
            new User(null, "common user", "password", "common-user@someemail.com", NotationUtil.hours, NotationUtil.notations, Role.COMMON),
            new User(null, "anonymous", "password", "anonymous@someemail.com", NotationUtil.hours, NotationUtil.notations, Role.ANONYMOUS)
    );

    public static User user = new User(users.get(0));
    public static User newUser = new User(users.get(0));
}
