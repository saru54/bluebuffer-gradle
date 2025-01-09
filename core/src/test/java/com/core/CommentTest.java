package com.core;

import com.core.common.PageResult;
import com.core.domain.*;
import com.core.domain.cq.*;
import com.core.domain.dto.CommentInfoDTO;
import com.core.domain.dto.SubCommentInfoDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest
public class CommentTest {
    @Autowired
    private ICommentRepository commentRepository;
    @Autowired
    private CommentDomainService commentDomainService;
    @Autowired
    private UserDomainService userDomainService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ClubDomainService clubDomainService;
    @Autowired
    private IClubRepository clubRepository;
    @Autowired
    private BlogDomainService blogDomainService;
    @Autowired
    private IBlogRepository blogRepository;
    @Test
    @Transactional
    public void testPageQuery() {
        userDomainService.registerByNameAndPassword("test","123", null,null);
        User user = userRepository.findOne(new UserQuery("test","123",null,null));
        clubDomainService.register(new ClubRegisterCommand("test", "介绍",null,"b7689931-0ba5-48c5-aed3-3421012f08d7"));

        Club club = clubRepository.findOne(new ClubQuery(null,"test"));
        Blog blog = blogDomainService.insert(new BlogInsertCommand(user.getId(),club.getId(),"title","content",null));


        List<String> images = new ArrayList<String>();
        images.add("asd");
        images.add("asdf");
        Comment comment = commentDomainService.insert(new CommentInsertCommand("content",user.getId(),blog.getId(),images));
        Comment comment2 = commentDomainService.insert(new CommentInsertCommand("content2",user.getId(),blog.getId(),images));
        Comment comment3 = commentDomainService.insert(new CommentInsertCommand("content3",user.getId(),blog.getId(),images));
        PageResult<CommentInfoDTO> pageResult = commentRepository.findCommentInfoByPage(new CommentPageQuery(1,2,blog.getId(),user.getId()));
        Assertions.assertEquals(3,pageResult.getCount());
        Assertions.assertEquals(2,pageResult.getRecords().size());
        Assertions.assertTrue(pageResult.getRecords().stream().anyMatch(c -> Objects.equals(c.getContent(), comment3.getContent())));




        blogRepository.delete(blog);
        userRepository.delete(user);
        clubRepository.delete(club);
        commentRepository.delete(comment);
        commentRepository.delete(comment2);
        commentRepository.delete(comment3);


    }
    @Test
    @Transactional
    public void testInsertAndDelete() {
        userDomainService.registerByNameAndPassword("test","123", null,null);
        User user = userRepository.findOne(new UserQuery("test","123",null,null));
        clubDomainService.register(new ClubRegisterCommand("test", "介绍",null,"b7689931-0ba5-48c5-aed3-3421012f08d7"));

        Club club = clubRepository.findOne(new ClubQuery(null,"test"));
        Blog blog = blogDomainService.insert(new BlogInsertCommand(user.getId(),club.getId(),"title","content",null));


        List<String> images = new ArrayList<String>();
        images.add("asd");
        images.add("asdf");
        Comment comment = commentDomainService.insert(new CommentInsertCommand("content",user.getId(),blog.getId(),images));
        PageResult<CommentInfoDTO> pageResult = commentRepository.findCommentInfoByPage(new CommentPageQuery(1,2,blog.getId(),user.getId()));
        CommentInfoDTO commentInfoDTO = pageResult.getRecords().get(0);
        Assertions.assertTrue(commentInfoDTO.getImages().stream().anyMatch(i-> i.equals(images.get(1))));
        blogRepository.delete(blog);
        userRepository.delete(user);
        clubRepository.delete(club);
        commentRepository.delete(comment);
    }
    @Test
    @Transactional
    public void testLike(){
        userDomainService.registerByNameAndPassword("test","123", null,null);
        User user = userRepository.findOne(new UserQuery("test","123",null,null));
        clubDomainService.register(new ClubRegisterCommand("test", "介绍",null,"b7689931-0ba5-48c5-aed3-3421012f08d7"));

        Club club = clubRepository.findOne(new ClubQuery(null,"test"));
        Blog blog = blogDomainService.insert(new BlogInsertCommand(user.getId(),club.getId(),"title","content",null));


        List<String> images = new ArrayList<String>();
        images.add("asd");
        images.add("asdf");
        Comment comment = commentDomainService.insert(new CommentInsertCommand("content",user.getId(),blog.getId(),images));
        commentRepository.like(user.getId(),comment.getId());
        PageResult<CommentInfoDTO> pageResult = commentRepository.findCommentInfoByPage(new CommentPageQuery(1,2,blog.getId(),user.getId()));
        CommentInfoDTO commentInfoDTO = pageResult.getRecords().get(0);
        Assertions.assertTrue(commentInfoDTO.getLike());
        commentRepository.like(user.getId(),comment.getId());
        PageResult<CommentInfoDTO> pageResult2 = commentRepository.findCommentInfoByPage(new CommentPageQuery(1,2,blog.getId(),user.getId()));
        CommentInfoDTO commentInfoDTO2 = pageResult2.getRecords().get(0);
        Assertions.assertFalse(commentInfoDTO2.getLike());
        blogRepository.delete(blog);
        userRepository.delete(user);
        clubRepository.delete(club);
        commentRepository.delete(comment);


    }
    @Test
    @Transactional
    public void testInsertSubCommentAndDelete(){
        userDomainService.registerByNameAndPassword("test","123", null,null);
        User user = userRepository.findOne(new UserQuery("test","123",null,null));
        userDomainService.registerByNameAndPassword("test2","123", null,null);
        User user2 = userRepository.findOne(new UserQuery("test2","123",null,null));
        clubDomainService.register(new ClubRegisterCommand("test", "介绍",null,"b7689931-0ba5-48c5-aed3-3421012f08d7"));

        Club club = clubRepository.findOne(new ClubQuery(null,"test"));
        Blog blog = blogDomainService.insert(new BlogInsertCommand(user.getId(),club.getId(),"title","content",null));


        List<String> images = new ArrayList<String>();
        images.add("asd");
        images.add("asdf");
        Comment comment = commentDomainService.insert(new CommentInsertCommand("content",user.getId(),blog.getId(),images));
        SubComment subComment = commentDomainService.insertSubComment(new SubCommentInsertCommand("content",comment.getId(),blog.getId(),user.getId(),user2.getId()));

        PageResult<SubCommentInfoDTO> pageResult = commentRepository.findSubCommentInfoByPage(new SubCommentPageQuery(1,1,comment.getId(),user.getId()));

        Assertions.assertTrue(pageResult.getRecords().stream().anyMatch(i-> i.getContent().equals(subComment.getContent())));
        Assertions.assertTrue(pageResult.getRecords().stream().anyMatch(i-> i.getAuthor().getId().equals(user.getId())));
        Assertions.assertTrue(pageResult.getRecords().stream().anyMatch(i-> i.getRespondUser().getId().equals(user2.getId())));
        blogRepository.delete(blog);
        userRepository.delete(user);
        clubRepository.delete(club);
        commentRepository.delete(comment);
        userRepository.delete(user2);
        commentRepository.deleteSubComment(subComment);
    }
    @Test
    @Transactional
    public void testPageQuerySubComment(){
        userDomainService.registerByNameAndPassword("test","123", null,null);
        User user = userRepository.findOne(new UserQuery("test","123",null,null));
        userDomainService.registerByNameAndPassword("test2","123", null,null);
        User user2 = userRepository.findOne(new UserQuery("test2","123",null,null));
        clubDomainService.register(new ClubRegisterCommand("test", "介绍",null,"b7689931-0ba5-48c5-aed3-3421012f08d7"));

        Club club = clubRepository.findOne(new ClubQuery(null,"test"));
        Blog blog = blogDomainService.insert(new BlogInsertCommand(user.getId(),club.getId(),"title","content",null));


        List<String> images = new ArrayList<String>();
        images.add("asd");
        images.add("asdf");
        Comment comment = commentDomainService.insert(new CommentInsertCommand("content",user.getId(),blog.getId(),images));
        SubComment subComment = commentDomainService.insertSubComment(new SubCommentInsertCommand("content1",comment.getId(),blog.getId(),user.getId(),user2.getId()));
        SubComment subComment2 = commentDomainService.insertSubComment(new SubCommentInsertCommand("content2",comment.getId(),blog.getId(),user.getId(),user2.getId()));
        PageResult<SubCommentInfoDTO> pageResult = commentRepository.findSubCommentInfoByPage(new SubCommentPageQuery(1,2,comment.getId(),user.getId()));
        Assertions.assertTrue(pageResult.getRecords().stream().anyMatch(i-> i.getContent().equals(subComment.getContent())));
        Assertions.assertTrue(pageResult.getRecords().stream().anyMatch(i-> i.getContent().equals(subComment2.getContent())));
        Assertions.assertEquals(2,pageResult.getRecords().size());
        blogRepository.delete(blog);
        userRepository.delete(user);
        clubRepository.delete(club);
        commentRepository.delete(comment);
        userRepository.delete(user2);
        commentRepository.deleteSubComment(subComment);
    }

}
