package cz.uhk.kppro.controller;

import cz.uhk.kppro.model.Category;
import cz.uhk.kppro.model.Comment;
import cz.uhk.kppro.model.Cuisine;
import cz.uhk.kppro.model.Recipe;
import cz.uhk.kppro.service.CategoryService;
import cz.uhk.kppro.service.CuisineService;
import cz.uhk.kppro.service.CommentService;
import cz.uhk.kppro.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CategoryService categoryService; // Added for categories

    @Autowired
    private CuisineService cuisineService; // Added for cuisines

    @GetMapping
    public String listRecipes(Model model, @RequestParam(required = false) Long cuisineId) {
        System.out.println("Selected Cuisine ID: " + cuisineId);

        List<Category> categories = categoryService.getAllCategories();
        System.out.println("Categories: " + categories);

        List<Cuisine> cuisines = cuisineService.getAllCuisines();
        System.out.println("Cuisines: " + cuisines);

        List<Recipe> recipes;
        if (cuisineId != null) {
            recipes = recipeService.getRecipesByCuisine(cuisineId);
            System.out.println("Filtered Recipes by Cuisine: " + recipes);
        } else {
            recipes = recipeService.getAllRecipes();
            System.out.println("All Recipes: " + recipes);
        }

        model.addAttribute("categories", categories);
        model.addAttribute("cuisines", cuisines);
        model.addAttribute("selectedCuisine", cuisineId);
        model.addAttribute("recipes", recipes);

        return "recipes/list";
    }


    @GetMapping("/{id}")
    public String viewRecipe(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.getRecipeById(id));
        model.addAttribute("comments", commentService.getCommentsByRecipeId(id));
        model.addAttribute("newComment", new Comment()); // Prepare an empty comment for the form
        return "recipes/detail";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "recipes/edit";
    }

    @PostMapping("/save")
    public String saveRecipe(@ModelAttribute Recipe recipe) {
        recipeService.saveRecipe(recipe);
        return "redirect:/recipes";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.getRecipeById(id)); // Load recipe for editing
        return "recipes/edit";
    }

    @PostMapping("/update")
    public String updateRecipe(@ModelAttribute Recipe recipe) {
        recipeService.saveRecipe(recipe); // Save the updated recipe
        return "redirect:/admin/dashboard"; // Redirect to the admin dashboard
    }

    @GetMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return "redirect:/admin/dashboard"; // Redirect to the admin dashboard
    }

    @PostMapping("/{id}/comment")
    public String addComment(@PathVariable Long id, @ModelAttribute("newComment") Comment comment) {
        // Fetch the currently logged-in user
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        // Set the username on the comment
        comment.setUsername(loggedInUsername);

        // Save the comment
        commentService.addCommentToRecipe(id, comment);

        return "redirect:/recipes/" + id;
    }
}
