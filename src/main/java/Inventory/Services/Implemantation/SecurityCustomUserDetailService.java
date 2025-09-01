package Inventory.Services.Implemantation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Inventory.Entity.Users;
import Inventory.Repositories.UsersRepository;

@Service
public class SecurityCustomUserDetailService implements UserDetailsService{

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // user load
       
        Users user =  usersRepository.findByEmail(username)
        .orElseThrow(()-> new UsernameNotFoundException("User not found with email : "+username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())   // must be encoded!
                                // or fetch from DB if you store roles
                .build();


    }

}
