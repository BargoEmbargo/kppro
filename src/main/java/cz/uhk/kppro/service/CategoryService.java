package cz.uhk.kppro.service;

import cz.uhk.kppro.model.Category;
import cz.uhk.kppro.model.Recipe;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    List<Recipe> getRecipesByCategory(Long categoryId);
    void saveCategory(Category category); // Add new category
    void deleteCategory(Long id); // Delete category by ID
}

