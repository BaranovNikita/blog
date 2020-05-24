package ru.nbaranov.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
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

    @JsonIgnore
    public Set<Post> getPosts() {
        return new HashSet<>();
    }
}
