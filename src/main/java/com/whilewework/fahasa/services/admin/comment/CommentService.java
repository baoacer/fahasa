package com.whilewework.fahasa.services.admin.comment;

import com.whilewework.fahasa.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentByProductId(Long productId);

    Comment addCommentToProduct(Long productId, Comment comment);

    void deleteComment(Long commentId);
}
