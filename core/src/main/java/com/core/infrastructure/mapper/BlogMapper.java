package com.core.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.core.domain.Blog;
import com.core.domain.cq.BlogPageQuery;
import com.core.domain.cq.BlogPageQueryWithConditionQuery;
import com.core.domain.cq.BlogQuery;
import com.core.domain.cq.BlogUpdateCommand;
import com.core.domain.dto.BlogBasicInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BlogMapper extends BaseMapper<Blog> {

    Blog findOne(BlogQuery query);

    Blog findById(String id);

    IPage<BlogBasicInfoDTO> findBasicInfoList(IPage<BlogBasicInfoDTO> page, @Param("query") BlogPageQuery query);

    void update(BlogUpdateCommand command);

    Page<BlogBasicInfoDTO> findBasicInfoListWithLike(@Param("page") Page<BlogBasicInfoDTO> page, @Param("query") BlogPageQueryWithConditionQuery query);
    Page<BlogBasicInfoDTO> findBasicInfoListWithCollect(@Param("page") Page<BlogBasicInfoDTO> page, @Param("query") BlogPageQueryWithConditionQuery query);

    Page<BlogBasicInfoDTO> findBasicInfoListWithOwn(@Param("page") Page<BlogBasicInfoDTO> page,@Param("query") BlogPageQueryWithConditionQuery query);
}
