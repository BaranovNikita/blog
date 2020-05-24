package ru.nbaranov.blog.controller;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.nbaranov.blog.services.CategoryService;
import ru.nbaranov.blog.services.PostService;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.core.AnyOf.anyOf;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
class CategoryControllerIntegrationTest {
    @MockBean
    private CategoryService categoryService;
    @MockBean
    private PostService postService;

    @Autowired
    CategoryController categoryController;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void whenPostRequestToCategoryAndValidCategory_thenCorrectResponse() throws Exception {
        var textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8);
        var category = "{\"title\": \"Super category\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/category")
                .content(category)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(textPlainUtf8));
    }

    @Test
    public void whenPostRequestToCategoryAndInvalidCategory_thenCorrectResponse() throws Exception {
        var category = "{\"title\": \"\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/category")
                .content(category)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", anyOf(Is.is("size must be between 3 and 40"), Is.is("Title is required"))))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }
}