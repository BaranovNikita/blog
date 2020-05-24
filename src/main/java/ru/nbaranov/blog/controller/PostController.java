package ru.nbaranov.blog.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.nbaranov.blog.entity.Post;
import ru.nbaranov.blog.services.CategoryService;
import ru.nbaranov.blog.services.PostService;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/post")
public class PostController {
    @Autowired
    PostService postService;
    @Autowired
    CategoryService categoryService;

    @GetMapping
    List<Post> getPosts(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        return postService.getPosts(page, limit);
    }

    @GetMapping("/{alias}")
    Post getPost(@PathVariable String alias) throws NotFoundException {
        var post = postService.getPostByAlias(alias);
        if (post == null) {
            throw new NotFoundException("");
        }
        post.setViewCount(post.getViewCount() + 1);
        postService.createPost(post);
        return post;
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("OK");
    }
    @PostMapping(value = { "/", "/{categories}" })
    Post createPost(@PathVariable("categories") Optional<Long[]> catIds, @Valid @RequestBody Post post) {
        if (catIds.isPresent()) {
            var categories = Arrays.stream(catIds.get()).map(id -> categoryService.getCategory(id)).filter(Objects::nonNull).collect(Collectors.toSet());
            post.setCategories(categories);
            try {
                return postService.createPost(post);
            } catch (Exception e) {
                return null;
            }

        }
        return null;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundExceptions(
            NotFoundException ex) {
        return "";
    }

}
