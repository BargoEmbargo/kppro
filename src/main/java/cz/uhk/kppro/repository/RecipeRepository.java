package cz.uhk.kppro.repository;

import cz.uhk.kppro.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByCuisineId(Long cuisineId);

}
