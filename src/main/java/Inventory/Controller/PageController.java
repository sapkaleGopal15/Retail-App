package Inventory.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import Inventory.Entity.Users;
import Inventory.Exception.Message;
import Inventory.Exception.MessageType;
import Inventory.Forms.UserForm;
import Inventory.Services.UserServices;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;



@Controller
public class PageController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/")
    public String index(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String homePage() {
        return "home";
    }

    @RequestMapping("/register")
    public String registerPage(Model model) {
        UserForm userForm = new UserForm();

        model.addAttribute("userForm", userForm);
        return "register";
    }

    @RequestMapping("/about")
    public String aboutPage() {
        return "about";
    }
    
    @RequestMapping("/service")
    public String servicePage() {
        return "service";
    }

    @RequestMapping("/contact")
    public String contactPage() {
        return "contact";
    }

     @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/forget-password")
    public String forgetLoginPage() {
        return "forget-password";
    }


    // =============================== Processing =============================

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult, HttpSession session) {

        System.out.println("Processing Method");

        // check validation
        if (rBindingResult.hasErrors()){
            return "register";
        }

        Users users = Users.builder()
        .fName(userForm.getFName())
        .lName(userForm.getLName())
        .email(userForm.getEmail())
        .contact(userForm.getContact())
        .address(userForm.getAddress())
        .password(userForm.getPassword())
        .shopName(userForm.getShopName())
        .shopGSTNumber(userForm.getShopGSTNumber())
        .shopAddress(userForm.getShopAddress())
        .build();

         Users savedUser = userServices.saveUsers(users);
        // Users savedUsers = userServices.setUserByTd(users);

        // Registration Successful

        Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();

        session.setAttribute("message", message);

        System.out.println("Saved user "+savedUser);
        // System.out.println(userForm);
        return "redirect:/register";
    }
}
