package com.core.controller.controller;

import com.core.common.PageResult;
import com.core.domain.BlogDomainService;
import com.core.domain.IBlogRepository;
import com.core.domain.cq.*;
import com.core.domain.dto.BlogBasicInfoDTO;
import com.core.domain.dto.BlogInfoDTO;
import com.core.util.LocalStoreUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/blog")
public class BlogController {
    private final IBlogRepository blogRepository;
    private final BlogDomainService blogDomainService;
    private final LocalStoreUtil localStoreUtil;
    public BlogController(IBlogRepository blogRepository, BlogDomainService blogDomainService, LocalStoreUtil localStoreUtil) {
        this.blogRepository = blogRepository;


        this.blogDomainService = blogDomainService;
        this.localStoreUtil = localStoreUtil;
    }
    @PostMapping("/insert")
    public ResponseEntity<String> insert(@RequestBody BlogInsertCommand command) {
        blogDomainService.insert(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody BlogDeleteCommand command) {
        if(!Objects.equals(localStoreUtil.getUserId(), command.getUserId())){
            return ResponseEntity.status(403).build();
        }
        blogDomainService.delete(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody BlogUpdateCommand command) {
        blogRepository.update(command);
        return ResponseEntity.ok("update success");
    }

    @PostMapping("/getBlogInfo")
    public ResponseEntity<BlogInfoDTO> getBlogInfo(@RequestBody BlogInfoQuery query) {
        if(!Objects.equals(localStoreUtil.getUserId(), query.getUserId())){
            return ResponseEntity.status(403).build();
        }
        BlogInfoDTO dto = blogDomainService.getBlogInfo(query);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/like")
    public ResponseEntity<String> like(@RequestParam String userId, @RequestParam String blogId) throws Exception {
        if(!Objects.equals(localStoreUtil.getUserId(), userId)){
            return ResponseEntity.status(403).build();
        }
        blogDomainService.like(userId,blogId);
        return ResponseEntity.ok("like success");
    }
    @PostMapping("/collect")
    public ResponseEntity<String> collect(@RequestParam String userId, @RequestParam String blogId) throws Exception {
        if(!Objects.equals(localStoreUtil.getUserId(), userId)){
            return ResponseEntity.status(403).build();
        }
        blogDomainService.collect(userId,blogId);
        return ResponseEntity.ok("collect success");
    }
    @PostMapping("/getByPage")
    public ResponseEntity<PageResult<BlogBasicInfoDTO>> getByPage(@RequestBody BlogPageQuery query) {
        PageResult<BlogBasicInfoDTO> pageResult = blogRepository.findBasicInfoListByPage(query);
        return ResponseEntity.ok(pageResult);
    }

    @PostMapping("/getCollectBlogByPage")
    public ResponseEntity<PageResult<BlogBasicInfoDTO>> getCollectBlogByPage(@RequestBody BlogPageQueryWithConditionQuery query) {
        if(!Objects.equals(localStoreUtil.getUserId(), query.getUserId())){
            return ResponseEntity.status(403).build();
        }
        PageResult<BlogBasicInfoDTO> pageResult = blogRepository.findBasicInfoByPageWithCollect(query);
        return ResponseEntity.ok(pageResult);
    }
    @PostMapping("/getLikeBlogByPage")
    public ResponseEntity<PageResult<BlogBasicInfoDTO>> getLikeBlogByPage(@RequestBody BlogPageQueryWithConditionQuery query) {
        if(!Objects.equals(localStoreUtil.getUserId(), query.getUserId())){
            return ResponseEntity.status(403).build();
        }
        PageResult<BlogBasicInfoDTO> pageResult = blogRepository.findBasicInfoByPageWithLike(query);
        return ResponseEntity.ok(pageResult);
    }
    @PostMapping("/getOwnBlogByPage")
    public ResponseEntity<PageResult<BlogBasicInfoDTO>> getOwnBlogByPage(@RequestBody BlogPageQueryWithConditionQuery query) {
        if(!Objects.equals(localStoreUtil.getUserId(), query.getUserId())){
            return ResponseEntity.status(403).build();
        }
        PageResult<BlogBasicInfoDTO> pageResult = blogRepository.findBasicInfoByPageWithOwn(query);
        return ResponseEntity.ok(pageResult);
    }
}
