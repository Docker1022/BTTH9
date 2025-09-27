package com.example.demo.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.Product;
import com.example.demo.User;
import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

import graphql.kickstart.tools.GraphQLMutationResolver;

@Component
public class MutationResolver implements GraphQLMutationResolver {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private ProductRepository productRepo;

    public User createUser(UserInput input) {
        User user = new User();
        user.setFullname(input.getFullname());
        user.setEmail(input.getEmail());
        user.setPassword(input.getPassword());  
        user.setPhone(input.getPhone());
        if (input.getCategoryIds() != null) {
            input.getCategoryIds().forEach(id -> {
                Category cat = categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
                user.getCategories().add(cat);
            });
        }
        return userRepo.save(user);
    }

    public User updateUser(Integer id, UserInput input) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (input.getFullname() != null) user.setFullname(input.getFullname());
        if (input.getEmail() != null) user.setEmail(input.getEmail());
        if (input.getPassword() != null) user.setPassword(input.getPassword()); 
        if (input.getPhone() != null) user.setPhone(input.getPhone());
        if (input.getCategoryIds() != null) {
            user.getCategories().clear();
            input.getCategoryIds().forEach(catId -> {
                Category cat = categoryRepo.findById(catId).orElseThrow(() -> new RuntimeException("Category not found"));
                user.getCategories().add(cat);
            });
        }
        return userRepo.save(user);
    }

    public Boolean deleteUser(Integer id) {
        userRepo.deleteById(id);
        return true;
    }

    public Category createCategory(CategoryInput input) {
        Category category = new Category();
        category.setName(input.getName());
        category.setImages(input.getImages());
        if (input.getUserIds() != null) {
            input.getUserIds().forEach(id -> {
                User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
                category.getUsers().add(user);
            });
        }
        return categoryRepo.save(category);
    }

    public Category updateCategory(Integer id, CategoryInput input) {
        Category category = categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        if (input.getName() != null) category.setName(input.getName());
        if (input.getImages() != null) category.setImages(input.getImages());
        if (input.getUserIds() != null) {
            category.getUsers().clear();
            input.getUserIds().forEach(userId -> {
                User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
                category.getUsers().add(user);
            });
        }
        return categoryRepo.save(category);
    }

    public Boolean deleteCategory(Integer id) {
        categoryRepo.deleteById(id);
        return true;
    }

    public Product createProduct(ProductInput input) {
        Product product = new Product();
        product.setTitle(input.getTitle());
        product.setQuantity(input.getQuantity());
        product.setDesc(input.getDesc());
        product.setPrice(input.getPrice());
        User user = userRepo.findById(input.getUserid()).orElseThrow(() -> new RuntimeException("User not found"));
        product.setUser(user);
        return productRepo.save(product);
    }

    public Product updateProduct(Integer id, ProductInput input) {
        Product product = productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        if (input.getTitle() != null) product.setTitle(input.getTitle());
        if (input.getQuantity() != null) product.setQuantity(input.getQuantity());
        if (input.getDesc() != null) product.setDesc(input.getDesc());
        if (input.getPrice() != null) product.setPrice(input.getPrice());
        if (input.getUserid() != null) {
            User user = userRepo.findById(input.getUserid()).orElseThrow(() -> new RuntimeException("User not found"));
            product.setUser(user);
        }
        return productRepo.save(product);
    }

    public Boolean deleteProduct(Integer id) {
        productRepo.deleteById(id);
        return true;
    }
}