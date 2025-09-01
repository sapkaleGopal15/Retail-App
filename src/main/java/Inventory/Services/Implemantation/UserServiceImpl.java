package Inventory.Services.Implemantation;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Inventory.Entity.Users;
import Inventory.Exception.AppConstants;
import Inventory.Exception.ResourceNotFoundException;
import Inventory.Repositories.UsersRepository;
import Inventory.Services.UserServices;

@Service
public class UserServiceImpl implements UserServices {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(this.getClass());



    @Override
    public Users saveUsers(Users users) {
        
        users.setPassword(passwordEncoder.encode(users.getPassword()));

        users.setRoleList(List.of(AppConstants.ROLE_USER));
        
        
        Users savedUser = usersRepository.save(users);
        return savedUser;
    }

    @Override
    public Optional<Users> updateUser(Users users) {
        Users users2 = usersRepository.findByEmail(users.getEmail()).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        // Update
        users2.setFName(users.getFName());
        users2.setLName(users.getLName());
        users2.setAddress(users.getAddress());
        users2.setContact(users.getContact());
        users2.setPassword(users.getPassword());
        users2.setShopAddress(users.getShopAddress());
        users2.setShopGSTNumber(users.getShopGSTNumber());
        users2.setShopName(users.getShopName());
        users2.setEnable(users.isEnabled());
        users2.setEmailVerified(users.isEmailVerified());
        users2.setPhoneVerified(users.isPhoneVerified());

        // save the user in database
        Users save = usersRepository.save(users2);
        return Optional.ofNullable(save);
    }

    @Override
    public boolean isUserExist(Long id) {
        Users users = usersRepository.findById(id).orElse(null);
        return users != null ? true : false;
    }

    @Override
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public Optional<Users> getUserById(Long id) {
        return usersRepository.findById(id);
    }

    @Override
    public void deleteUser(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }

    @Override
    public Users setUserByTd(Users id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setUserByTd'");
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isUserExistByEmail'");
    }

    @Override
    public Users getUserByEmail(String email) {
        
        return usersRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Users findByEmail(String name) {
        return usersRepository.findByEmail(name).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public boolean isUserExist(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isUserExist'");
    }

}
