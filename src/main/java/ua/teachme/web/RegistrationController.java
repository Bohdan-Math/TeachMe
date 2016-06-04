package ua.teachme.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.teachme.dto.UserTO;
import ua.teachme.service.UserService;
import ua.teachme.util.user.UserUtil;

@Controller
@RequestMapping( value = "/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String getRegistrationForm(){
        return "registration";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String userRegistration(UserTO userTO, Model model){
        userService.save(UserUtil.saveUser(userTO));
        model.addAttribute("message", true);
        model.addAttribute("loginError", false);
        return "login";
    }

}
