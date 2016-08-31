package kr.iolo.springboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue
    public Long id;

    @Column(unique = true)
    public String username;

    @JsonIgnore
    @Column
    public String password;

    //@JsonIgnore
    //@OneToMany
    //public List<Post> posts;

    //@JsonIgnore
    //@OneToMany
    //public List<Comment> comments;
}
