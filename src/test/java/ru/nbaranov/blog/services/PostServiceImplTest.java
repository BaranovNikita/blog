package ru.nbaranov.blog.services;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nbaranov.blog.entity.Post;
import ru.nbaranov.blog.reportistory.PostRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
class PostServiceImplTest {
    @MockBean
    private PostRepository postRepository;
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
    public void testGetByAlias() {
        given(postRepository.findByAlias(any())).willReturn(getTestPost());
        Post post = postService.getPostByAlias("alias");
        assertThat(post.getAlias()).isEqualTo("alias");
    }
}