package ru.nbaranov.blog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.nbaranov.blog.entity.Post;
import ru.nbaranov.blog.exception.EntityAlreadyExists;
import ru.nbaranov.blog.reportistory.PostRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;

    @Override
    public Post createPost(Post post) throws EntityAlreadyExists {
        try {
            return postRepository.save(post);
        } catch (DataIntegrityViolationException e) {
            throw new EntityAlreadyExists("alias", "Post with current alias already exists");
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Post updatePost(Long id, Map<String, Object> properties) throws InvocationTargetException, IllegalAccessException, EntityAlreadyExists {
        var existsPost = postRepository.findById(id);
        if (existsPost.isPresent()) {
            var post = existsPost.get();
            org.apache.commons.beanutils.BeanUtils.populate(post, properties);
            try {
                return postRepository.save(post);
            } catch (DataIntegrityViolationException e) {
                throw new EntityAlreadyExists("alias", "Category with current alias already exists");
            }
        }
        return null;
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Post getPostByAlias(String alias) {
        return postRepository.findByAlias(alias);
    }

    @Override
    public List<Post> getPosts(int page, int limit) {
        var pr = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        return postRepository.findAll(pr).getContent();
    }
}
