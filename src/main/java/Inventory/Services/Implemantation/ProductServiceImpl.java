package Inventory.Services.Implemantation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Inventory.Entity.Products;
import Inventory.Entity.Users;
import Inventory.Repositories.ProductRepository;
import Inventory.Services.productServices;

@Service
public class ProductServiceImpl implements productServices {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Products saveProducts(Products products) {
        
        Products saved = productRepository.save(products);

        return saved;
    }

    @Override
    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Products> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Optional<Products> updateProduct(Products products) {
        Products products2 = productRepository.findByNameContainingIgnoreCase(products.getName()).get(0);
        products2.setName(products.getName());
        products2.setCompany(products.getCompany());
        products2.setPrice(products.getPrice());
        products2.setQuantity(products.getQuantity());
        products2.setDescription(products.getDescription());

        Products products3 = productRepository.save(products2);

        return Optional.ofNullable(products3);
    }

    @Override
    public void updateProduct(Long id, Products updatedProduct) {
        Products product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(updatedProduct.getName());
        product.setCompany(updatedProduct.getCompany());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setQuantity(updatedProduct.getQuantity());
        product.setExpiryDate(updatedProduct.getExpiryDate());
        product.setManufacturingDate(updatedProduct.getManufacturingDate());

        productRepository.save(product);
    }

    @Override
    public Object getProductsByUser(Users currentUser) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductsByUser'");
    }

    @Override
    public List<Products> searchProductsByUserAndName(Users currentUser, String keyword) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchProductsByUserAndName'");
    }
}
