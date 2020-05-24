package ru.nbaranov.blog.services;

import ru.nbaranov.blog.entity.Post;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public interface PostService {
    Post createPost(Post post);
    Post updatePost(Long id, Map<String, Object> properties) throws InvocationTargetException, IllegalAccessException;
    void deletePost(Long id);
    Post getPostByAlias(String alias);
}
