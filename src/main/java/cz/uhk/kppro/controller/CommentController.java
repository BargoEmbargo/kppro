package cz.uhk.kppro.controller;

import cz.uhk.kppro.model.Comment;
import cz.uhk.kppro.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/{recipeId}")
    public Comment addComment(@PathVariable Long recipeId, @RequestBody Comment comment) {
        // Debug input
        System.out.println("Comment from request: " + comment);

        // Ensure ID is null for new comments
        comment.setId(null);

        return commentService.addCommentToRecipe(recipeId, comment);
    }


    @GetMapping("/{recipeId}")
    public List<Comment> getComments(@PathVariable Long recipeId) {
        return commentService.getCommentsByRecipeId(recipeId); // Fetch comments for the recipe
    }
}
