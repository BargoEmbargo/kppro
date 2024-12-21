package cz.uhk.kppro.service;

import cz.uhk.kppro.model.Cuisine;
import cz.uhk.kppro.model.Recipe;

import java.util.List;

public interface CuisineService {
    List<Cuisine> getAllCuisines();
    List<Recipe> getRecipesByCuisine(Long cuisineId);
    void saveCuisine(Cuisine cuisine); // Add new cuisine
    void deleteCuisine(Long id); // Delete cuisine by ID
}

