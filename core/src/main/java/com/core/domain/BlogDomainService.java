package com.core.domain;


import com.core.domain.cq.BlogDeleteCommand;
import com.core.domain.cq.BlogInfoQuery;
import com.core.domain.cq.BlogInsertCommand;
import com.core.domain.cq.BlogQuery;
import com.core.domain.dto.BlogInfoDTO;
import com.core.domain.dto.ClubBasicInfoDTO;
import com.core.domain.dto.UserBasicInfoDTO;
import org.springframework.stereotype.Service;

@Service
public class BlogDomainService {
    private final IBlogRepository blogRepository;

    public BlogDomainService(IBlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Blog insert(BlogInsertCommand command) {

        Blog blog = new Blog(command.getTitle(),command.getContent(),command.getUserId(),command.getClubId(),command.getImages());
        blogRepository.insert(blog);
        return blog;

    }

    public BlogInfoDTO getBlogInfo(BlogInfoQuery query) {
        Blog blog = blogRepository.findOne(new BlogQuery(query.getId()));
        UserBasicInfoDTO userBasicInfoDTO = blog.getAuthor();

        ClubBasicInfoDTO clubBasicInfoDTO = blog.getClub();
        Boolean like = blogRepository.checkBlogLikeCondition(query.getUserId(),query.getId());
        Boolean collect = blogRepository.checkBlogCollectCondition(query.getUserId(),query.getId());

        return new BlogInfoDTO(blog.getId(),blog.getTitle(),blog.getContent(),blog.getImages(),clubBasicInfoDTO,userBasicInfoDTO,blog.getCreateTime(),like,collect);
    }

    public void like(String userId, String blogId) throws Exception {
        Blog blog=  blogRepository.findOne(new BlogQuery(blogId));
        if (blog==null){
            return;
        }
        if(!blogRepository.checkBlogLikeCondition(userId,blogId)){
            blogRepository.addBlogLike(userId,blogId);
        }else{
            blogRepository.deleteBlogLike(userId,blogId);
        }
    }
    public void collect(String userId, String blogId) {
        Blog blog= blogRepository.findOne(new BlogQuery(blogId));
        if (blog==null){
            return;
        }
        if(!blogRepository.checkBlogCollectCondition(userId,blogId)){
            blogRepository.addBlogCollect(userId,blogId);
        }else{
            blogRepository.deleteBlogCollect(userId,blogId);
        }
    }

    public void delete(BlogDeleteCommand command) {

        Blog blog = blogRepository.findOne(new BlogQuery(command.getId()));
        if(blog!=null){
            blogRepository.delete(blog);
        }
    }
}
