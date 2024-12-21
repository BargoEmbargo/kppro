package cz.uhk.kppro;

import cz.uhk.kppro.model.Category;
import cz.uhk.kppro.model.Cuisine;
import cz.uhk.kppro.model.Recipe;
import cz.uhk.kppro.model.User;
import cz.uhk.kppro.service.CategoryService;
import cz.uhk.kppro.service.CuisineService;
import cz.uhk.kppro.service.RecipeService;
import cz.uhk.kppro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class KpproApplication {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public KpproApplication(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner demo() {
        return (args) -> {
            addUser("admin", "heslo", "ROLE_ADMIN");
            addUser("user", "heslo", "ROLE_USER");
        };
    }

    @Bean
    public CommandLineRunner seedData(RecipeService recipeService, CategoryService categoryService, CuisineService cuisineService) {
        return args -> {
            // Seed categories
            Category category1 = addCategory(categoryService, "Main Course");
            Category category2 = addCategory(categoryService, "Appetizer");
            Category category3 = addCategory(categoryService, "Dessert");

            // Seed regional cuisines
            Cuisine cuisine1 = addCuisine(cuisineService, "Ohrid", "Specialities from Ohrid, like Ohrid trout.");
            Cuisine cuisine2 = addCuisine(cuisineService, "Skopje", "Traditional dishes from the capital city.");
            Cuisine cuisine3 = addCuisine(cuisineService, "Bitola", "Famous regional stews and hearty dishes.");

            // Seed recipes with categories and regional cuisines
            if (recipeService.getAllRecipes().isEmpty()) {
                addRecipe(recipeService, "Tavče Gravče", "Traditional baked beans",
                        "Beans, onion, paprika, oil, flour, parsley",
                        "Soak beans, boil, prepare paprika sauce, bake.", category1, cuisine2);
                addRecipe(recipeService, "Ajvar", "Roasted red pepper spread",
                        "Red peppers, eggplant, garlic, oil, salt",
                        "Roast peppers and eggplant, blend, simmer, jar.", category2, cuisine1);
                addRecipe(recipeService, "Shopska Salad", "Refreshing salad with fresh vegetables",
                        "Tomatoes, cucumbers, onions, peppers, feta cheese, parsley",
                        "Chop vegetables, mix, top with cheese.", category2, cuisine2);
                addRecipe(recipeService, "Pastrmajlija", "Flatbread topped with seasoned meat",
                        "Flour, yeast, pork, paprika, oil, salt",
                        "Make dough, season meat, assemble, bake.", category1, cuisine3);
                addRecipe(recipeService, "Ohrid Trout", "Freshwater trout from Ohrid",
                        "Ohrid trout, lemon, garlic, parsley, olive oil",
                        "Grill trout with olive oil and herbs.", category1, cuisine1);
                addRecipe(recipeService, "Kadaif", "Sweet shredded pastry dessert",
                        "Shredded phyllo dough, sugar syrup, walnuts, butter",
                        "Layer phyllo dough, add nuts, bake, and soak in syrup.", category3, cuisine3); // Dessert
            }
        };
    }

    private Category addCategory(CategoryService categoryService, String name) {
        Category category = new Category();
        category.setName(name);
        categoryService.saveCategory(category);
        return category;
    }

    private Cuisine addCuisine(CuisineService cuisineService, String name, String description) {
        Cuisine cuisine = new Cuisine();
        cuisine.setName(name);
        cuisine.setDescription(description);
        cuisineService.saveCuisine(cuisine);
        return cuisine;
    }

    private void addRecipe(RecipeService recipeService, String title, String description, String ingredients, String steps,
                           Category category, Cuisine cuisine) {
        Recipe recipe = new Recipe();
        recipe.setTitle(title);
        recipe.setDescription(description);
        recipe.setIngredients(ingredients);
        recipe.setSteps(steps);
        recipe.setCategory(category);
        recipe.setCuisine(cuisine);
        recipeService.saveRecipe(recipe);
    }

    private void addUser(String username, String password, String role) {
        if (userService.findByUsername(username) == null) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(role);
            userService.save(user);
            System.out.println("User created: " + username + " / " + password);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(KpproApplication.class, args);
    }
}
