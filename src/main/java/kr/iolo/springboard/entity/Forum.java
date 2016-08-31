package kr.iolo.springboard.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Forum {

    @Id
    @GeneratedValue
    public Long id;

    @Column
    public String title;

    //@JsonIgnore
    //@OneToMany
    //public List<Post> posts;
}
