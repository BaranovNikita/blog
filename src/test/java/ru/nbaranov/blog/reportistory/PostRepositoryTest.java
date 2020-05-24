package ru.nbaranov.blog.reportistory;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nbaranov.blog.entity.Post;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class PostRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    private Post getTestPost() {
        var post = new Post();
        post.setAlias("alias");
        post.setTitle("test post title");
        post.setAuthor("author");
        post.setText("text");
        post.setDescription("description");
        return post;
    }

    @Test
    public void whenFindByAlias_thenReturnPost() {
        var post = getTestPost();
        entityManager.persist(post);
        entityManager.flush();

        var found = postRepository.findByAlias(post.getAlias());

        assertThat(found.getTitle())
                .isEqualTo(post.getTitle());
    }

    @Test
    public void whenDeleteByID_thenNotExists() {
        var post = getTestPost();
        var id = entityManager.persist(post).getId();
        entityManager.flush();

        postRepository.deleteById(id);
        var existsPost = postRepository.existsById(id);
        assertThat(existsPost).isEqualTo(false);
    }
}