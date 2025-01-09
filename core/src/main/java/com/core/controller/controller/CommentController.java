package com.core.controller.controller;

import com.core.common.PageResult;
import com.core.domain.CommentDomainService;
import com.core.domain.ICommentRepository;
import com.core.domain.cq.*;
import com.core.domain.dto.CommentInfoDTO;
import com.core.domain.dto.SubCommentInfoDTO;
import com.core.util.LocalStoreUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentDomainService commentDomainService;
    private final LocalStoreUtil localStoreUtil;
    private final ICommentRepository commentRepository;
    public CommentController(CommentDomainService commentDomainService, LocalStoreUtil localStoreUtil, ICommentRepository commentRepository) {
        this.commentDomainService = commentDomainService;
        this.localStoreUtil = localStoreUtil;
        this.commentRepository = commentRepository;
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insert(@RequestBody CommentInsertCommand command) {
        commentDomainService.insert(command);
        return  ResponseEntity.ok("insert success");

    }
    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody CommentDeleteCommand command) {
        if(!localStoreUtil.getUserId().equals(command.userId())){
            return ResponseEntity.status(403).build();
        }
        commentDomainService.delete(command);
        return  ResponseEntity.ok("delete success");
    }

    @PostMapping("/getByPage")
    public ResponseEntity<PageResult<CommentInfoDTO>> getByPage(@RequestBody CommentPageQuery query) {
        PageResult<CommentInfoDTO>  pageResult = commentRepository.findCommentInfoByPage(query);
        return ResponseEntity.ok(pageResult);
    }
    @PostMapping("/getSubCommentByPage")
    public ResponseEntity<PageResult<SubCommentInfoDTO>> getSubCommentByPage(@RequestBody SubCommentPageQuery query) {
        PageResult<SubCommentInfoDTO> pageResult =  commentRepository.findSubCommentInfoByPage(query);
        return ResponseEntity.ok(pageResult);
    }
    @PostMapping("/insertSubComment")
    public ResponseEntity<String> insertSubComment(@RequestBody CommentInsertCommand command) {
        commentDomainService.insert(command);
        return  ResponseEntity.ok("insert success");
    }

    @PostMapping("/like")
    public ResponseEntity<String> like(@RequestParam String userId,@RequestParam String commentId) {
        if(!Objects.equals(localStoreUtil.getUserId(), userId)){
            return ResponseEntity.status(403).build();
        }
        commentRepository.like(userId,commentId);
        return  ResponseEntity.ok("like");
    }
    @PostMapping("/likeSubComment")
    public ResponseEntity<String> likeSubComment(@RequestParam String userId, @RequestParam String subCommentId) {
        if(!Objects.equals(localStoreUtil.getUserId(), userId)){
            return ResponseEntity.status(403).build();
        }
        return  ResponseEntity.ok("likeSubComment");
    }
}
