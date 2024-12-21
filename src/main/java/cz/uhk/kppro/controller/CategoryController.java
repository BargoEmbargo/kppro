package cz.uhk.kppro.controller;

import cz.uhk.kppro.model.Category;
import cz.uhk.kppro.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("newCategory", new Category()); // Add an empty Category for the form
        return "admin/categories";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute("newCategory") Category category) {
        categoryService.saveCategory(category); // Save the new category
        return "redirect:/admin/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id); // Delete the category by ID
        return "redirect:/admin/categories";
    }
}


