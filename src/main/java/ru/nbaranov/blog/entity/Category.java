package ru.nbaranov.blog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category extends AbstractEntity {
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 40)
    @Column(length = 40, unique = true)
    private String title;
    @ManyToMany(mappedBy = "categories")
    private Set<Post> posts = new HashSet<>();

    public Category(String title) {
        this.title = title;
    }
}
