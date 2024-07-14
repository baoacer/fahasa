package com.whilewework.fahasa.services.admin.comment;

import com.whilewework.fahasa.entity.Comment;
import com.whilewework.fahasa.entity.Product;
import com.whilewework.fahasa.exceptions.ValidationException;
import com.whilewework.fahasa.repository.CommentRepository;
import com.whilewework.fahasa.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;

    @Override
    public List<Comment> getCommentByProductId(Long productId){
        return commentRepository.findByProductId(productId);
    }

    @Override
    public Comment addCommentToProduct(Long productId, Comment comment) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ValidationException("Product not found"));

        comment.setProduct(product);
        comment.setDate(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

}
