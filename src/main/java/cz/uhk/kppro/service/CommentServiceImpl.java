package cz.uhk.kppro.service;

import cz.uhk.kppro.model.Comment;
import cz.uhk.kppro.model.Recipe;
import cz.uhk.kppro.repository.CommentRepository;
import cz.uhk.kppro.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public Comment addCommentToRecipe(Long recipeId, Comment comment) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        // Ensure the Comment is a new entity
        comment.setId(null); // Reset the ID to ensure a new entity is created
        comment.setRecipe(recipe);

        return commentRepository.save(comment);
    }





    @Override
    public List<Comment> getCommentsByRecipeId(Long recipeId) {
        return commentRepository.findByRecipeId(recipeId); // Fetch comments by recipe ID
    }
}
