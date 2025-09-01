package Inventory.Controller;
import Inventory.Entity.Users;
import Inventory.Exception.Helper;
import Inventory.Exception.ResourceNotFoundException;
import Inventory.Repositories.UsersRepository;
import Inventory.Services.UserServices;
import Inventory.Services.UsersService;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/user")
@Controller
public class UsersController {

    @Autowired
    private UserServices usersService;

    @Autowired
    private UsersService usersService2;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsersController (UsersService usersService){
        this.usersService2 = usersService;
    }

    private Logger logger = LoggerFactory.getLogger(UsersController.class);

    @RequestMapping(value = "/dashboard")
    public String userDashboard() {
        return "user/dashboard";
    }

    @RequestMapping(value = "/edit-profile")
    public String editProfile(){
        return "user/edit-profile";
    }
    
    @RequestMapping(value = "/profile")
    public String userProfile(Model model, Authentication authentication) {
        String userName = Helper.getEmailOfLoggedInUser(authentication);

        logger.info("User logged in: {}", userName);

        Users user = usersService.getUserByEmail(userName);

        // System.out.println(user.getFName());
        // System.out.println(user.getLName());
        // System.out.println(user.getPassword());
        
    
    
        model.addAttribute("loggedInUser",user);
        // System.out.println("User loggin id : "+userName);
        return "user/profile";
    }

    @GetMapping("/forget-password")
    public String forgetPasswordPage() {
        return "forget-password"; // Thymeleaf template
    }


    @PostMapping("/forget-password")
    public String resetPassword(@RequestParam("email") String email,
                                @RequestParam("newPassword") String newPassword,
                                Model model) {
            System.out.println("Update password controller called");
        boolean success = usersService2.resetPassword(email, newPassword);
        if(success) {
            model.addAttribute("successMessage", "Password reset successfully! You can now login.");
        } else {
            model.addAttribute("errorMessage", "Email not found! Please check again.");
        }
        return "forget-password"; // same page with message
    }

}

