package com.careerdevs.geekylikes.entities.relationship;


import com.careerdevs.geekylikes.entities.developer.Developer;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import javax.persistence.*;

@Entity
public class Relationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="originator_id", referencedColumnName = "id", table="developer")
    @JsonIncludeProperties("id")
    private Developer developer;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="recipient_id", referencedColumnName = "id", table="developer")
    @JsonIncludeProperties("id")
    private Developer recipient;

    @Enumerated
    private ERelationship type;

    public Relationship() {}

    public Relationship(Developer developer, Developer recipient, ERelationship type) {
        this.developer = developer;
        this.recipient = recipient;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public Developer getRecipient() {
        return recipient;
    }

    public void setRecipient(Developer recipient) {
        this.recipient = recipient;
    }

    public ERelationship getType() {return type;}

    public void setType(ERelationship type) {
        this.type = type;
    }
}
