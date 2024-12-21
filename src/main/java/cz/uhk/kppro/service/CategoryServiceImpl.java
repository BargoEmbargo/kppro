package cz.uhk.kppro.service;

import cz.uhk.kppro.model.Category;
import cz.uhk.kppro.model.Recipe;
import cz.uhk.kppro.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Recipe> getRecipesByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return category.getRecipes();
    }

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(category); // Save or update category
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id); // Delete category by ID
    }
}

