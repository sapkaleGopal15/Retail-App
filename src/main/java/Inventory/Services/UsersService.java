package Inventory.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Inventory.Entity.Users;
import Inventory.Repositories.UsersRepository;
import jakarta.transaction.Transactional;


@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository, PasswordEncoder
     passwordEncoder){
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public boolean resetPassword(String email, String newPassword) {
    
        return usersRepository.findByEmail(email)
            .map(users -> {
                users.setPassword(passwordEncoder
                    .encode(newPassword));
                usersRepository.save(users);
            return true;
        }).orElse(false);
    }


    public Users saveUser(Users users) {
        return usersRepository.save(users);
    }


    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public Users getUserByEmail(String email) {
        return usersRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not exist!"));
    }

    public Users registerUser(Users user) {
        if (usersRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists!");
        }
        return usersRepository.save(user);
    }



  
}
