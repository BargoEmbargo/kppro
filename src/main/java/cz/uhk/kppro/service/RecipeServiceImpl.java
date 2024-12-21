package cz.uhk.kppro.service;

import cz.uhk.kppro.model.Recipe;
import cz.uhk.kppro.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new RuntimeException("Recipe not found"));
    }

    @Override
    public List<Recipe> getRecipesByCuisine(Long cuisineId) {
        return recipeRepository.findByCuisineId(cuisineId);
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    @Override
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }
}
