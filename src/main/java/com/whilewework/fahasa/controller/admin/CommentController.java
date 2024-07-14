package com.whilewework.fahasa.controller.admin;

import com.whilewework.fahasa.entity.Comment;
import com.whilewework.fahasa.services.admin.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/{productId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public List<Comment> getCommentsByProductId(@PathVariable Long productId) {
        return commentService.getCommentByProductId(productId);
    }

    @PostMapping
    public Comment addCommentToProduct(@PathVariable Long productId, @RequestBody Comment comment) {
        return commentService.addCommentToProduct(productId, comment);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }

}
