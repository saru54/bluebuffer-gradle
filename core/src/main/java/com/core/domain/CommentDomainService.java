package com.core.domain;

import com.core.domain.cq.CommentDeleteCommand;
import com.core.domain.cq.CommentInsertCommand;
import com.core.domain.cq.SubCommentInsertCommand;
import org.springframework.stereotype.Service;

@Service
public class CommentDomainService {
    private final ICommentRepository commentRepository;
    public CommentDomainService(ICommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment insert(CommentInsertCommand command) {
        Comment comment = new Comment(command.content(),command.userId(),command.blogId(),command.images());

        commentRepository.insert(comment);
        return comment;
    }

    public void delete(CommentDeleteCommand command) {
        Comment  comment = commentRepository.findById(command.id());
        if(comment != null){
            commentRepository.delete(comment);
        }
    }


    public SubComment insertSubComment(SubCommentInsertCommand command) {
        SubComment subComment = new SubComment(command.getContent(),command.getCommentId(),command.getBlogId(),command.getUserId(),command.getRespondUserId());
        commentRepository.insertSubComment(subComment);
        return subComment;
    }
}
