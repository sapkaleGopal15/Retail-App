package Inventory.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Inventory.Entity.Users;
import jakarta.transaction.Transactional;

import java.util.Optional;



@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);

    Optional<Users> findByEmailAndPassword(String email, String password);

    Optional<Users> findByEmailToken(String id);

    Optional<Users> findById(Long id);

    boolean existsByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Users u WHERE u.shopGSTNumber = :shopGSTNumber")
    boolean existsByGstNumber(@Param("shopGSTNumber") String shopGSTNumber);

    void save(Optional<Users> user);


    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.password = :password WHERE u.email = :email")
    int updatePasswordByEmail(String email, String password);


}
