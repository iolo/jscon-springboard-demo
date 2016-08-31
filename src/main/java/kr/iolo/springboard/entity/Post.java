package kr.iolo.springboard.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    public User user;
    //public Long userId;

    @ManyToOne
    public Forum forum;
    //public Long forumId;

    @Column
    public String title;

    @Column(columnDefinition = "TEXT")
    public String content;

    @Column
    public Date createdAt = new Date();

    //@JsonIgnore
    //@OneToMany
    //public List<Comment> comments;
}
