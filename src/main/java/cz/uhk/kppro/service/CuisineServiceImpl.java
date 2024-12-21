package cz.uhk.kppro.service;

import cz.uhk.kppro.model.Cuisine;
import cz.uhk.kppro.model.Recipe;
import cz.uhk.kppro.repository.CuisineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuisineServiceImpl implements CuisineService {

    @Autowired
    private CuisineRepository cuisineRepository;

    @Override
    public List<Cuisine> getAllCuisines() {
        return cuisineRepository.findAll();
    }

    @Override
    public List<Recipe> getRecipesByCuisine(Long cuisineId) {
        Cuisine cuisine = cuisineRepository.findById(cuisineId)
                .orElseThrow(() -> new RuntimeException("Cuisine not found"));
        return cuisine.getRecipes();
    }

    @Override
    public void saveCuisine(Cuisine cuisine) {
        cuisineRepository.save(cuisine); // Save or update cuisine
    }

    @Override
    public void deleteCuisine(Long id) {
        cuisineRepository.deleteById(id); // Delete cuisine by ID
    }
}

