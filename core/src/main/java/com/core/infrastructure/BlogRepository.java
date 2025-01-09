package com.core.infrastructure;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.core.common.PageResult;
import com.core.domain.Blog;
import com.core.domain.IBlogRepository;
import com.core.domain.cq.BlogPageQuery;
import com.core.domain.cq.BlogPageQueryWithConditionQuery;
import com.core.domain.cq.BlogQuery;
import com.core.domain.cq.BlogUpdateCommand;
import com.core.domain.dto.BlogBasicInfoDTO;
import com.core.domain.entity.BlogImage;
import com.core.domain.entity.ClubBlog;
import com.core.domain.entity.UserBlogCollect;
import com.core.domain.entity.UserBlogLike;
import com.core.infrastructure.mapper.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BlogRepository  implements IBlogRepository {
    private final BlogMapper blogMapper;
    private final UserMapper userMapper;
    private final ClubMapper clubMapper;
    private final BlogImageMapper blogImageMapper;
    private final ClubBlogMapper clubBlogMapper;
    private final UserBlogLikeMapper userBlogLikeMapper;
    private final UserBlogCollectMapper userBlogCollectMapper;

    public BlogRepository(BlogMapper blogMapper, UserMapper userMapper, ClubMapper clubMapper, BlogImageMapper blogImageMapper, ClubBlogMapper clubBlogMapper,  UserBlogLikeMapper userBlogLikeMapper, UserBlogCollectMapper userBlogCollectMapper) {
        this.blogMapper = blogMapper;
        this.userMapper = userMapper;
        this.clubMapper = clubMapper;
        this.blogImageMapper = blogImageMapper;
        this.clubBlogMapper = clubBlogMapper;

        this.userBlogLikeMapper = userBlogLikeMapper;
        this.userBlogCollectMapper = userBlogCollectMapper;
    }

    @Override
    public Blog findOne(BlogQuery query) {
        return blogMapper.findOne(query);
    }


    @Transactional
    @Override
    public void insert(Blog blog) {
        blogMapper.insert(blog);
        clubBlogMapper.insert(new ClubBlog(blog.getClubId(),blog.getId()));
        if(blog.getImages() != null) {
            List<BlogImage> blogImages = blog.getImages().stream().map(i->new BlogImage(blog.getId(),i)).collect(Collectors.toList());
            blogImageMapper.insert(blogImages);
        }

    }

    @Override
    public void update(BlogUpdateCommand command) {
        blogMapper.update(command);
    }
    @Transactional
    @Override
    public void delete(Blog blog) {
        blogMapper.deleteById(blog.getId());
        if(blog.getImages() != null) {
            LambdaQueryWrapper<BlogImage> wrapper = Wrappers.<BlogImage>lambdaQuery();
            wrapper.eq(BlogImage::getBlogId, blog.getId());
            blogImageMapper.delete(wrapper);
        }

    }



    @Override
    public PageResult<BlogBasicInfoDTO> findBasicInfoListByPage(BlogPageQuery query) {
        Page<BlogBasicInfoDTO> page = new Page<>(query.getPage(),query.getPageSize());
        IPage<BlogBasicInfoDTO> findPage = blogMapper.findBasicInfoList(page,query);
        PageResult<BlogBasicInfoDTO> pageResult = new PageResult<>();
        pageResult.setCurrentPage(findPage.getCurrent());
        pageResult.setCount(findPage.getTotal());
        pageResult.setRecords(findPage.getRecords());
        return pageResult;
    }

    @Override
    public boolean checkBlogLikeCondition(String userId, String blogId) {
        LambdaQueryWrapper<UserBlogLike> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserBlogLike::getUserId, userId);
        wrapper.eq(UserBlogLike::getBlogId, blogId);
        UserBlogLike userBlogLike = userBlogLikeMapper.selectOne(wrapper);
        return  userBlogLike!=null;
    }

    @Override
    public void addBlogLike(String userId, String blogId) throws Exception {
        int i = userBlogLikeMapper.insert(new UserBlogLike(userId,blogId));
        if(i == -1){
            throw new Exception("insert blog like failed");
        }
    }

    @Override
    public void deleteBlogLike(String userId, String blogId) {
        LambdaQueryWrapper<UserBlogLike> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserBlogLike::getUserId, userId);
        wrapper.eq(UserBlogLike::getBlogId, blogId);
        userBlogLikeMapper.delete(wrapper);
    }

    @Override
    public boolean checkBlogCollectCondition(String userId, String blogId) {
        LambdaQueryWrapper<UserBlogCollect> wrapper =new LambdaQueryWrapper<>();
        wrapper.eq(UserBlogCollect::getUserId, userId);
        wrapper.eq(UserBlogCollect::getBlogId, blogId);
        return userBlogCollectMapper.selectOne(wrapper)!=null;
    }

    @Override
    public void addBlogCollect(String userId, String blogId) {
        userBlogCollectMapper.insert(new UserBlogCollect(userId,blogId));
    }

    @Override
    public void deleteBlogCollect(String userId, String blogId) {
        LambdaQueryWrapper<UserBlogCollect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserBlogCollect::getUserId, userId);
        wrapper.eq(UserBlogCollect::getBlogId, blogId);
        userBlogCollectMapper.delete(wrapper);
    }

    @Override
    public PageResult<BlogBasicInfoDTO> findBasicInfoByPageWithLike(BlogPageQueryWithConditionQuery query) {
        PageResult<BlogBasicInfoDTO> pageResult = new PageResult<>();
        Page<BlogBasicInfoDTO> page = new Page<>(query.getPage(),query.getPageSize());
        page = blogMapper.findBasicInfoListWithLike(page,query);
        pageResult.setCurrentPage(page.getCurrent());
        pageResult.setCount(page.getTotal());
        pageResult.setRecords(page.getRecords());
        return pageResult;
    }
    @Override
    public PageResult<BlogBasicInfoDTO> findBasicInfoByPageWithCollect(BlogPageQueryWithConditionQuery query) {
        PageResult<BlogBasicInfoDTO> pageResult = new PageResult<>();
        Page<BlogBasicInfoDTO> page = new Page<>(query.getPage(),query.getPageSize());
        page = blogMapper.findBasicInfoListWithCollect(page,query);
        pageResult.setCurrentPage(page.getCurrent());
        pageResult.setCount(page.getTotal());
        pageResult.setRecords(page.getRecords());
        return pageResult;
    }

    @Override
    public PageResult<BlogBasicInfoDTO> findBasicInfoByPageWithOwn(BlogPageQueryWithConditionQuery query) {
        PageResult<BlogBasicInfoDTO> pageResult = new PageResult<>();
        Page<BlogBasicInfoDTO> page = new Page<>(query.getPage(),query.getPageSize());
        page = blogMapper.findBasicInfoListWithOwn(page,query);
        pageResult.setCurrentPage(page.getCurrent());
        pageResult.setCount(page.getTotal());
        pageResult.setRecords(page.getRecords());
        return pageResult;
    }
}
