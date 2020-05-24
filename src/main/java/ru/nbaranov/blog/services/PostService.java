package ru.nbaranov.blog.services;

import ru.nbaranov.blog.entity.Post;
import ru.nbaranov.blog.exception.EntityAlreadyExists;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public interface PostService {
    Post createPost(Post post) throws EntityAlreadyExists;
    Post updatePost(Long id, Map<String, Object> properties) throws InvocationTargetException, IllegalAccessException, EntityAlreadyExists;
    void deletePost(Long id);
    Post getPostByAlias(String alias);
    List<Post> getPosts(int page, int limit);
}
