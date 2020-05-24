package ru.nbaranov.blog.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Data
public abstract class AbstractEntity {
    @Id
    @GeneratedValue
    Long id;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date updatedAt;
}
