package com.careerdevs.geekylikes.entities.language;


import com.careerdevs.geekylikes.entities.developer.Developer;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Language {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String tag;


//    @ManyToMany
//    @JoinTable(
//            name="developer_language",
//            joinColumns = @JoinColumn(name = "language_id"),
//            inverseJoinColumns = @JoinColumn(name = "developer_id")
//    )
//    @JsonIgnoreProperties({"languages"})
//    private Set<Developer> developers = new HashSet<>();

    public Language() {
    }

    public Language(String name, String tag) {
        this.name = name;
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

 }

