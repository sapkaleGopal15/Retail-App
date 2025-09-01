package Inventory.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import Inventory.Entity.Products;
import Inventory.Entity.Users;
import Inventory.Exception.Message;
import Inventory.Exception.MessageType;
import Inventory.Services.ProductService;
import Inventory.Services.UsersService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UsersService usersService;

    // ================= Add Product =================
    @GetMapping("/add")
    public String addProductView(Model model) {
        model.addAttribute("productForm", new Products());
        return "user/product/add";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Products productForm, Principal principal, HttpSession session) {

        Users currentUser = usersService.getUserByEmail(principal.getName());
        productForm.setUsers(currentUser);

        productService.saveProduct(productForm);

        session.setAttribute("message", new Message("Product Added Successfully", MessageType.green));
        return "redirect:/user/product/add";
    }

    // ================= Search Products =================
    @GetMapping("/search")
    public String searchProductsPage(Model model, Principal principal) {
        Users currentUser = usersService.getUserByEmail(principal.getName());
        List<Products> products = productService.getProductsByUser(currentUser);
        model.addAttribute("products", products);
        return "user/product/search";
    }


    @GetMapping("/search-products")
    @ResponseBody
    public List<Products> searchProducts(@RequestParam(required = false) String keyword, Principal principal) {
        Users user = usersService.getUserByEmail(principal.getName());
        if (keyword == null || keyword.isEmpty()) {
            return productService.getProductsByUser(user);
        }
        return productService.searchProductsByNameAndUser(keyword, user);
    }

    // ================= Update Products =================
    @GetMapping("/update-select")
    public String selectProductToUpdate(Model model, Principal principal) {
        Users currentUser = usersService.getUserByEmail(principal.getName());
        model.addAttribute("products", productService.getProductsByUser(currentUser));
        return "user/product/update-select";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model, Principal principal) {
        Users currentUser = usersService.getUserByEmail(principal.getName());
        Products product = productService.getProductById(id, currentUser);
        model.addAttribute("product", product);
        return "user/product/update";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Products updatedProduct,
                                Principal principal, HttpSession session) {
        Users currentUser = usersService.getUserByEmail(principal.getName());
        productService.updateProduct(id, updatedProduct, currentUser);
        session.setAttribute("message", new Message("Product Updated Successfully", MessageType.green));
        return "redirect:/user/product/update-select";
    }

    // ================= Delete Products =================
    @GetMapping("/delete")
    public String selectProductToDelete(Model model, Principal principal) {
        Users currentUser = usersService.getUserByEmail(principal.getName());
        model.addAttribute("products", productService.getProductsByUser(currentUser));
        return "user/product/delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteProductAjax(@PathVariable Long id, Principal principal, HttpSession session) {
        Users currentUser = usersService.getUserByEmail(principal.getName());
            productService.deleteProduct(id, currentUser);
            session.setAttribute("message", new Message("Product Deleted Successfully", MessageType.green));
            return "redirect:/user/product/delete";
            // return ResponseEntity.noContent().build();
    }
}
