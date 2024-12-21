package cz.uhk.kppro.controller;

import cz.uhk.kppro.model.Recipe;
import cz.uhk.kppro.service.CategoryService;
import cz.uhk.kppro.service.CuisineService;
import cz.uhk.kppro.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CategoryService categoryService; // Add this

    @Autowired
    private CuisineService cuisineService; // Add this

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("recipes", recipeService.getAllRecipes());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("cuisines", cuisineService.getAllCuisines());
        model.addAttribute("newRecipe", new Recipe());
        return "admin/dashboard";
    }


    @PostMapping("/recipe/add")
    public String addRecipe(@ModelAttribute("newRecipe") Recipe recipe) {
        recipeService.saveRecipe(recipe); // Save the new recipe
        return "redirect:/admin/dashboard"; // Redirect back to the dashboard
    }
}
