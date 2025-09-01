package Inventory.Services;

import java.util.List;
import java.util.Optional;

import Inventory.Entity.Users;

public interface UserServices {

    Users saveUsers(Users users);
    Users setUserByTd(Users id);
    Optional<Users> getUserById(Long id);
    Optional<Users> updateUser(Users users);
    void deleteUser(Long id);
    boolean isUserExist(String email);
    boolean isUserExistByEmail(String email);
    List<Users> getAllUsers();
    Users getUserByEmail(String email);
    Users findByEmail(String name);
    boolean isUserExist(Long id);
}
