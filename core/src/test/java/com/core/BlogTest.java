package com.core;


import com.core.common.PageResult;
import com.core.domain.*;
import com.core.domain.cq.*;
import com.core.domain.dto.BlogBasicInfoDTO;
import com.core.domain.dto.BlogInfoDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BlogTest {


    @Autowired
    private IBlogRepository blogRepository;
    @Autowired
    private BlogDomainService blogDomainService;
    @Autowired
    private UserDomainService userDomainService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ClubDomainService clubDomainService;
    @Autowired
    private IClubRepository clubRepository;


    @Transactional
    @Test
    public void testInsertAndDelete() {

        userDomainService.registerByNameAndPassword("test","123", null,null);
        User user = userRepository.findOne(new UserQuery("test","123",null,null));
        clubDomainService.register(new ClubRegisterCommand("test", "介绍",null,"b7689931-0ba5-48c5-aed3-3421012f08d7"));

        Club club = clubRepository.findOne(new ClubQuery(null,"test"));
        Blog blog = blogDomainService.insert(new BlogInsertCommand(user.getId(),club.getId(),"title","content",null));
        Blog findBlog = blogRepository.findOne(new BlogQuery(blog.getId()));
        Assertions.assertNotNull(findBlog);
        Assertions.assertEquals(blog.getTitle(),findBlog.getTitle());
        blogRepository.delete(blog);
        userRepository.delete(user);
        clubRepository.delete(club);
    }
    @Test
    @Transactional
    public void testGetBlogInfo(){
        userDomainService.registerByNameAndPassword("test","123", null,null);
        User user = userRepository.findOne(new UserQuery("test","123",null,null));
        clubDomainService.register(new ClubRegisterCommand("test", "介绍",null,"b7689931-0ba5-48c5-aed3-3421012f08d7"));
        List<String> images = new ArrayList<>();
        images.add("asd");
        images.add("asdf");
        Club club = clubRepository.findOne(new ClubQuery(null,"test"));
        Blog blog = blogDomainService.insert(new BlogInsertCommand(user.getId(),club.getId(),"title","content",images));
        BlogInfoDTO blogInfoDTO = blogDomainService.getBlogInfo(new BlogInfoQuery(blog.getId(),user.getId()));
        Assertions.assertNotNull(blogInfoDTO);
        Assertions.assertEquals(blog.getTitle(),blogInfoDTO.getTitle());
        Assertions.assertEquals(club.getName(),blogInfoDTO.getClub().getName());
        Assertions.assertEquals(user.getName(),blogInfoDTO.getUser().getName());
        Assertions.assertEquals(images.size(),blogInfoDTO.getImages().size());

        blogRepository.delete(blog);
        userRepository.delete(user);
        clubRepository.delete(club);
    }

    @Test
    @Transactional
    public void testGetBlogByPage(){
        userDomainService.registerByNameAndPassword("test","123", null,null);
        User user = userRepository.findOne(new UserQuery("test","123",null,null));
        clubDomainService.register(new ClubRegisterCommand("test", "介绍",null,"b7689931-0ba5-48c5-aed3-3421012f08d7"));
        Club club = clubRepository.findOne(new ClubQuery(null,"test"));
        Blog blog = blogDomainService.insert(new BlogInsertCommand(user.getId(),club.getId(),"title","content",null));
        Blog blog2 = blogDomainService.insert(new BlogInsertCommand(user.getId(),club.getId(),"title2","content2",null));

        PageResult<BlogBasicInfoDTO> page = blogRepository.findBasicInfoListByPage(new BlogPageQuery(2,1,club.getId()));
        Assertions.assertNotNull(page);
        Assertions.assertEquals(2,page.getCount());
        Assertions.assertEquals(2,page.getCurrentPage());
        userRepository.delete(user);
        clubRepository.delete(club);
        blogRepository.delete(blog);
        blogRepository.delete(blog2);
    }

    @Test
    @Transactional
    public void testUpdate(){
        userDomainService.registerByNameAndPassword("test","123", null,null);
        User user = userRepository.findOne(new UserQuery("test","123",null,null));
        clubDomainService.register(new ClubRegisterCommand("test", "介绍",null,"b7689931-0ba5-48c5-aed3-3421012f08d7"));

        Club club = clubRepository.findOne(new ClubQuery(null,"test"));
        Blog blog = blogDomainService.insert(new BlogInsertCommand(user.getId(),club.getId(),"title","content",null));

        blogRepository.update(new BlogUpdateCommand(blog.getId(),null,"update"));
        Blog updateBlog = blogRepository.findOne(new BlogQuery(blog.getId()));
        Assertions.assertNotNull(updateBlog);
        Assertions.assertEquals("update",updateBlog.getContent());
        userRepository.delete(user);
        clubRepository.delete(club);
        blogRepository.delete(blog);

    }


    @Test
    @Transactional
    public void testLikeAndCollect() throws Exception {
        userDomainService.registerByNameAndPassword("test","123", null,null);
        User user = userRepository.findOne(new UserQuery("test","123",null,null));
        clubDomainService.register(new ClubRegisterCommand("test", "介绍",null,"b7689931-0ba5-48c5-aed3-3421012f08d7"));

        Club club = clubRepository.findOne(new ClubQuery(null,"test"));
        Blog blog = blogDomainService.insert(new BlogInsertCommand(user.getId(),club.getId(),"title","content",null));
        BlogInfoDTO blogInfoDTO = blogDomainService.getBlogInfo(new BlogInfoQuery(blog.getId(),user.getId()));
        Assertions.assertNotNull(blogInfoDTO);


        blogDomainService.like(user.getId(),blog.getId());
        blogDomainService.collect(user.getId(),blog.getId());
        BlogInfoDTO updateBlogInfoDTO = blogDomainService.getBlogInfo(new BlogInfoQuery(blog.getId(),user.getId()));
        Assertions.assertTrue(blogRepository.checkBlogLikeCondition(user.getId(), blog.getId()));
        Assertions.assertTrue(blogRepository.checkBlogCollectCondition(user.getId(), blog.getId()));
        Assertions.assertEquals(true,updateBlogInfoDTO.getLike());
        Assertions.assertEquals(true,updateBlogInfoDTO.getCollect());

        blogDomainService.like(user.getId(),blog.getId());
        blogDomainService.collect(user.getId(),blog.getId());
        Assertions.assertFalse(blogRepository.checkBlogLikeCondition(user.getId(), blog.getId()));
        Assertions.assertFalse(blogRepository.checkBlogCollectCondition(club.getId(), blog.getId()));
        Assertions.assertEquals(false,blogInfoDTO.getLike());
        Assertions.assertEquals(false,blogInfoDTO.getCollect());
        userRepository.delete(user);
        clubRepository.delete(club);
        blogRepository.delete(blog);
    }
    @Test
    @Transactional
    public void testPageQuery() throws InterruptedException {
        userDomainService.registerByNameAndPassword("testUser","123", "userImage",null);
        User user = userRepository.findOne(new UserQuery("testUser","123",null,null));
        clubDomainService.register(new ClubRegisterCommand("testClub", "介绍","clubImage","b7689931-0ba5-48c5-aed3-3421012f08d7"));
        List<String> images = new ArrayList<String>();
        images.add("image1");
        images.add("image2");
        Club club = clubRepository.findOne(new ClubQuery(null,"testClub"));
        Blog blog = blogDomainService.insert(new BlogInsertCommand(user.getId(),club.getId(),"title","content",null));
        Thread.sleep(1000);
        Blog blog2 = blogDomainService.insert(new BlogInsertCommand(user.getId(),club.getId(),"title2","content2",images));
        PageResult<BlogBasicInfoDTO> pageResult = blogRepository.findBasicInfoListByPage(new BlogPageQuery(1,1,club.getId()));
        Assertions.assertNotNull(pageResult);
        Assertions.assertEquals(2,pageResult.getCount());
        Assertions.assertEquals(blog2.getTitle(),pageResult.getRecords().get(0).getTitle());
    }
    @Test
    @Transactional
    public void testGetLikeBlogAndCollectBlogByPage() throws Exception {
        userDomainService.registerByNameAndPassword("test","123", null,null);
        User user = userRepository.findOne(new UserQuery("test","123",null,null));
        clubDomainService.register(new ClubRegisterCommand("test", "介绍",null,"b7689931-0ba5-48c5-aed3-3421012f08d7"));

        Club club = clubRepository.findOne(new ClubQuery(null,"test"));
        Blog blog = blogDomainService.insert(new BlogInsertCommand(user.getId(),club.getId(),"title","content",null));
        Blog blog2 = blogDomainService.insert(new BlogInsertCommand(user.getId(),club.getId(),"title2","content2",null));
        blogDomainService.like(user.getId(),blog.getId());
        blogDomainService.collect(user.getId(),blog.getId());
        blogDomainService.like(user.getId(),blog2.getId());
        blogDomainService.collect(user.getId(),blog2.getId());
        PageResult<BlogBasicInfoDTO> likeBlogs = blogRepository.findBasicInfoByPageWithLike(new BlogPageQueryWithConditionQuery(1,2,user.getId()));
        PageResult<BlogBasicInfoDTO> collectBlogs =blogRepository.findBasicInfoByPageWithCollect(new BlogPageQueryWithConditionQuery(1,2,user.getId()));
        Assertions.assertEquals(2,likeBlogs.getRecords().stream().count());
        Assertions.assertNotNull(collectBlogs.getRecords());
        Assertions.assertTrue(likeBlogs.getRecords().stream().anyMatch(blogBasicInfoDTO ->
                blogBasicInfoDTO.getTitle().equals("title2")));

        Assertions.assertTrue(collectBlogs.getRecords().stream().anyMatch(blogBasicInfoDTO ->
                blogBasicInfoDTO.getTitle().equals("title2")));
        userRepository.delete(user);
        clubRepository.delete(club);
        blogRepository.delete(blog);
        blogRepository.delete(blog2);
    }


    @Test
    @Transactional
    public void testGetOwnBlog(){
        userDomainService.registerByNameAndPassword("test","123", null,null);
        User user = userRepository.findOne(new UserQuery("test","123",null,null));
        clubDomainService.register(new ClubRegisterCommand("test", "介绍",null,"b7689931-0ba5-48c5-aed3-3421012f08d7"));

        Club club = clubRepository.findOne(new ClubQuery(null,"test"));
        Blog blog = blogDomainService.insert(new BlogInsertCommand(user.getId(),club.getId(),"title1","content1",null));
        Blog blog2 = blogDomainService.insert(new BlogInsertCommand(user.getId(),club.getId(),"title2","content2",null));
        PageResult<BlogBasicInfoDTO> pageResult = blogRepository.findBasicInfoByPageWithOwn(new BlogPageQueryWithConditionQuery(1,2,user.getId()));
        Assertions.assertTrue(pageResult.getRecords().stream().anyMatch(blogBasicInfoDTO -> blogBasicInfoDTO.getTitle().equals(blog2.getTitle())));
        Assertions.assertTrue(pageResult.getRecords().stream().anyMatch(blogBasicInfoDTO -> blogBasicInfoDTO.getTitle().equals(blog.getTitle())));
        userRepository.delete(user);
        clubRepository.delete(club);
        blogRepository.delete(blog);
    }
}
