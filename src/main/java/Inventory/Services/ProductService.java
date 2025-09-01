package Inventory.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Inventory.Entity.Products;
import Inventory.Entity.Users;
import Inventory.Repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Save or update product
    public Products saveProduct(Products product) {
        return productRepository.save(product);
    }

    // Get product by id (check ownership)
    public Products getProductById(Long id, Users user) {
        Products product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (!product.getUsers().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Unauthorized access");
        }
        return product;
    }

    // Get all products of a user
    public List<Products> getProductsByUser(Users user) {
        return productRepository.findByUsers(user);
    }

    // Search products by name for a user
    public List<Products> searchProductsByNameAndUser(String name, Users user) {
        return productRepository.findByUsersAndNameContainingIgnoreCase(user, name);
    }

    // Update product
    public Products updateProduct(Long id, Products updatedProduct, Users user) {
        Products product = getProductById(id, user);
        product.setName(updatedProduct.getName());
        product.setCompany(updatedProduct.getCompany());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setQuantity(updatedProduct.getQuantity());
        product.setManufacturingDate(updatedProduct.getManufacturingDate());
        product.setExpiryDate(updatedProduct.getExpiryDate());
        return productRepository.save(product);
    }

    // Delete product
    public void deleteProduct(Long id, Users user) {
        Products product = getProductById(id, user);
        productRepository.delete(product);
    }
}
