package ru.nbaranov.blog.reportistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nbaranov.blog.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByAlias(String alias);
}
