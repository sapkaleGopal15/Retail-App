package Inventory.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import Inventory.Entity.Users;
import Inventory.Exception.Helper;
import Inventory.Services.UserServices;

@ControllerAdvice
public class RootController {

    private Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UserServices userServices;

    @ModelAttribute
    public void addLogginUserInfo(Model model, Authentication authentication){

        if (authentication == null) {
            return ;
        }
        //System.out.println("User Information");
        String userName = Helper.getEmailOfLoggedInUser(authentication);

        logger.info("User logged in: {}", userName);

        //System.out.println(userName);

        Users user = userServices.getUserByEmail(userName);

        // System.out.println(user.getFName());
        // System.out.println(user.getLName());
        // System.out.println(user.getPassword());

        model.addAttribute("loggedInUser",user);

    }
}
