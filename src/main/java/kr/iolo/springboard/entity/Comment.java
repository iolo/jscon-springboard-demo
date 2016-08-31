package kr.iolo.springboard.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    public User user;
    //public Long userId;

    @ManyToOne
    public Post post;
    //public Long postId;

    @Column(columnDefinition = "TEXT")
    public String content;

    @Column
    public Date createdAt = new Date();
}
