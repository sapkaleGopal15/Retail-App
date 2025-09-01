package Inventory.Controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Inventory.Entity.Users;
import Inventory.Repositories.UsersRepository;

@Controller
@RequestMapping("/user/profile")
public class UserProfileController {

    @Autowired
    private UsersRepository userRepository;

    @GetMapping("/edit")
    public String editProfile(Model model, Principal principal) {
        String email = principal.getName(); // assuming email is the username
        Optional<Users> user = userRepository.findByEmail(email);
        model.addAttribute("loggedInUser", user);
        return "user/edit-profile";
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute("loggedInUser") Users updatedUser, Principal principal) {
        Optional<Users> optionalUser = userRepository.findByEmail(principal.getName());

        if (optionalUser.isPresent()) {
            Users user = optionalUser.get(); // unwrap Optional

            user.setFName(updatedUser.getFName());
            user.setLName(updatedUser.getLName());
            //user.setEmail(updatedUser.getEmail());
            user.setContact(updatedUser.getContact()); // adjust field name to match your entity
            user.setAddress(updatedUser.getAddress());
            user.setShopName(updatedUser.getShopName());
            user.setShopAddress(updatedUser.getShopAddress());
            
            userRepository.save(user);
        }

        return "redirect:/user/profile";
    }

}

