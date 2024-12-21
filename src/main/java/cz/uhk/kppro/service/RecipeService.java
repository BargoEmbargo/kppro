package cz.uhk.kppro.service;

import cz.uhk.kppro.model.Recipe;
import java.util.List;

public interface RecipeService {
    List<Recipe> getAllRecipes();
    Recipe getRecipeById(Long id);
    void saveRecipe(Recipe recipe);
    void deleteRecipe(Long id);
    List<Recipe> getRecipesByCuisine(Long cuisineId); // New Method
}
