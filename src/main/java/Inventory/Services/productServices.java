package Inventory.Services;

import java.util.List;
import java.util.Optional;

import Inventory.Entity.Products;
import Inventory.Entity.Users;


public interface productServices {

    Products saveProducts(Products products);

    List<Products> getAllProducts();

    List<Products> searchProductsByName(String name);

    Optional<Products> updateProduct(Products products);

    void updateProduct(Long id, Products product);

    Object getProductsByUser(Users currentUser);

    List<Products> searchProductsByUserAndName(Users currentUser, String keyword);

}
