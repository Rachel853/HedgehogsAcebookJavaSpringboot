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
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
    @javax.persistence.Transient private Long likes;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private java.sql.Timestamp createdAt;

    public Comment(String content, User user, Post post, java.sql.Timestamp createdAt) {
        this.content = content;
        this.user = user;
        this.post = post;
        this.createdAt = createdAt;
    }

}
