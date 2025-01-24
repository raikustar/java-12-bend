package com.rainer.veebipood.controller;

import com.rainer.veebipood.entity.Category;
import com.rainer.veebipood.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    //List<Product> tooted = new ArrayList<>(Arrays.asList(
    //        new Product("Coca", 1.1, true, 150),
    //        new Product("Fanta", 1.0, true, 100),
    //       new Product("Sprite", 1.2, false, 0)
    //));

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("category")
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @PostMapping("category")
    public List<Category> addCategory(@RequestBody Category newCategory) {
        categoryRepository.save(newCategory);
        return categoryRepository.findAll();
    }

    @DeleteMapping("category/{id}")
    public List<Category> deleteCategory(@PathVariable Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Ei saa kustutada katergooriat mille sees on toode");
        }
        return categoryRepository.findAll();

    }
}

