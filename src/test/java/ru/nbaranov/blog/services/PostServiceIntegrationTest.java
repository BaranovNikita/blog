package ru.nbaranov.blog.services;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nbaranov.blog.entity.Post;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceIntegrationTest {
    @Autowired
    private PostService postService;

    private Post getTestPost() {
        var post = new Post();
        post.setAlias("alias");
        post.setTitle("test post title");
        post.setAuthor("author");
        post.setText("text");
        return post;
    }

    @Test
    public void testUpdate() throws InvocationTargetException, IllegalAccessException {
        var post = getTestPost();
        var savedPost = postService.createPost(post);
        var id = savedPost.getId();
        var parameters = new HashMap<String, Object>();
        parameters.put("title", "updated title test post");
        parameters.put("text", "updated text test post");
        var updatedPost = postService.updatePost(id, parameters);
        assertTrue("Updated post has correct title", updatedPost.getTitle().contains("updated"));
        assertTrue("Updated post has correct text", updatedPost.getText().contains("updated"));
    }
}
