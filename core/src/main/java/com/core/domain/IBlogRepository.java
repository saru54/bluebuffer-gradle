package com.core.domain;


import com.core.common.PageResult;
import com.core.domain.cq.BlogPageQuery;
import com.core.domain.cq.BlogPageQueryWithConditionQuery;
import com.core.domain.cq.BlogQuery;
import com.core.domain.cq.BlogUpdateCommand;
import com.core.domain.dto.BlogBasicInfoDTO;

public interface IBlogRepository {
    Blog findOne(BlogQuery query);

    void insert(Blog blog);
    void update(BlogUpdateCommand command);
    void delete(Blog blog);

    PageResult<BlogBasicInfoDTO> findBasicInfoListByPage(BlogPageQuery query);


    boolean checkBlogLikeCondition(String userId, String blogId);

    void addBlogLike(String userId, String blogId) throws Exception;

    void deleteBlogLike(String userId, String blogId);

    boolean checkBlogCollectCondition(String userId, String blogId);

    void addBlogCollect(String userId, String blogId);

    void deleteBlogCollect(String userId, String blogId);

    PageResult<BlogBasicInfoDTO> findBasicInfoByPageWithLike(BlogPageQueryWithConditionQuery blogPageQueryWithConditionQuery);

    PageResult<BlogBasicInfoDTO> findBasicInfoByPageWithCollect(BlogPageQueryWithConditionQuery query);

    PageResult<BlogBasicInfoDTO> findBasicInfoByPageWithOwn(BlogPageQueryWithConditionQuery query);
}
