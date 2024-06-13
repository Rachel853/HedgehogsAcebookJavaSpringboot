package com.makersacademy.acebook.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;


@Data
@Entity
@Table(name = "COMMENTS")
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Getter
    private String content;
    private Long userId;
    private Long postId;
    @javax.persistence.Transient private Long likes;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private java.sql.Timestamp createdAt;

    public Comment(String content, Long userId, Long postId, java.sql.Timestamp createdAt) {
        this.content = content;
        this.userId = userId;
        this.postId = postId;
        this.createdAt = createdAt;
    }

}
