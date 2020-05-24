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
@Table(name = "post")
public class Post extends AbstractEntity {
    @NotBlank
    @Size(min = 10, max = 200)
    @Column(length = 200)
    private String title;
    @NotBlank
    private String author;
    @Lob
    @NotBlank
    private String text;
    @NotBlank
    private String alias;
    private int viewCount = 0;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "post_category",
            joinColumns = { @JoinColumn(name = "post_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") }
    )
    Set<Category> categories = new HashSet<>();
}
