package com.example.demo.resolver;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.Product;
import com.example.demo.User;
import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

import graphql.kickstart.tools.GraphQLQueryResolver;

@Component
public class QueryResolver implements GraphQLQueryResolver {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    public List<Product> allProductsSortedByPrice() {
        return productRepo.findAll().stream()
                .sorted((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()))
                .collect(Collectors.toList());
    }

    public List<Product> productsByCategory(Integer categoryId) {
        Category cat = categoryRepo.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        return cat.getUsers().stream()
                .flatMap(u -> u.getProducts().stream())
                .collect(Collectors.toList());
    }

    public List<User> users() {
        return userRepo.findAll();
    }

    public User user(Integer id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<Category> categories() {
        return categoryRepo.findAll();
    }

    public Category category(Integer id) {
        return categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public List<Product> products() {
        return productRepo.findAll();
    }

    public Product product(Integer id) {
        return productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }
}