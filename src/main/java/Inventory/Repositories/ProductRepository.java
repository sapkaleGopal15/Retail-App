package Inventory.Repositories;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Inventory.Entity.Products;
import Inventory.Entity.Users;




public interface ProductRepository extends JpaRepository<Products, Long> {

    List<Products> findByNameContainingIgnoreCase(String name);
    // Optional<Products> findById(Long id);
    List<Products> findByUsers_Id(Long userId);

    // ✅ Fetch all products by user
    List<Products> findByUsers(Users user);

    // ✅ Search products by name (case-insensitive) for a specific user
    List<Products> findByUsersAndNameContainingIgnoreCase(Users user, String name);


    // 1) All products for a user (used when keyword is empty)
    @Query("""
           SELECT p FROM Products p
           WHERE p.users.id = :userId
           ORDER BY p.id DESC
           """)
    List<Products> findAllByUser(@Param("userId") Long userId);

    // 2) Keyword search for a user: name/company/price/expiry (string match)
    @Query("""
           SELECT p FROM Products p
           WHERE p.users.id = :userId
             AND (
                 LOWER(p.name)    LIKE LOWER(CONCAT('%', :kw, '%'))
              OR LOWER(p.company) LIKE LOWER(CONCAT('%', :kw, '%'))
              OR CAST(p.price AS string) LIKE CONCAT('%', :kw, '%')
              OR CAST(p.expiryDate AS string) LIKE CONCAT('%', :kw, '%')
             )
           ORDER BY p.id DESC
           """)
    List<Products> searchForUser(@Param("userId") Long userId, @Param("kw") String kw);

    // 3) Exact match on expiry date for a user (when user types dd-MM-uuuu)
    @Query("""
           SELECT p FROM Products p
           WHERE p.users.id = :userId
             AND p.expiryDate = :date
           ORDER BY p.id DESC
           """)
    List<Products> findByUserAndExpiry(@Param("userId") Long userId, @Param("date") LocalDate date);


    // List<Products> searchByExpiryDate(Long id, LocalDate date);

    @Query("SELECT p FROM Products p " +
           "WHERE p.users.id = :userId " +
           "AND p.expiryDate = :expiryDate")
    List<Products> searchByExpiryDate(@Param("userId") Long userId,
                                      @Param("expiryDate") LocalDate expiryDate);

    // List<Products> searchByKeyword(Long id, String keyword);

    @Query("SELECT p FROM Products p WHERE p.users.id = :userId AND (p.name LIKE %:keyword% OR p.description LIKE %:keyword%)")
    List<Products> searchByKeyword(@Param("userId") Long userId, @Param("keyword") String keyword);
}

   