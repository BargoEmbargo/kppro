package cz.uhk.kppro.controller;

import cz.uhk.kppro.model.Cuisine;
import cz.uhk.kppro.service.CuisineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/cuisines")
public class CuisineController {

    @Autowired
    private CuisineService cuisineService;

    @GetMapping
    public String listCuisines(Model model) {
        model.addAttribute("cuisines", cuisineService.getAllCuisines());
        model.addAttribute("newCuisine", new Cuisine()); // Add an empty Cuisine for the form
        return "admin/cuisines";
    }

    @PostMapping("/add")
    public String addCuisine(@ModelAttribute("newCuisine") Cuisine cuisine) {
        cuisineService.saveCuisine(cuisine); // Save the new cuisine
        return "redirect:/admin/cuisines";
    }

    @GetMapping("/delete/{id}")
    public String deleteCuisine(@PathVariable Long id) {
        cuisineService.deleteCuisine(id); // Delete the cuisine by ID
        return "redirect:/admin/cuisines";
    }
}

