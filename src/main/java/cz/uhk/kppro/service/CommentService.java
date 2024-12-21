package cz.uhk.kppro.service;

import cz.uhk.kppro.model.Comment;

import java.util.List;

public interface CommentService {
    Comment addCommentToRecipe(Long recipeId, Comment comment); // Add a comment to a specific recipe
    List<Comment> getCommentsByRecipeId(Long recipeId); // Retrieve comments for a specific recipe
}
