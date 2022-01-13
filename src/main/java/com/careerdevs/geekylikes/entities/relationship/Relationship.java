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
    @JoinColumn(name="originator_id", referencedColumnName = "id")
    @JsonIncludeProperties("id")
    private Developer originator;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="recipient_id", referencedColumnName = "id")
    @JsonIncludeProperties("id")
    private Developer recipient;

    @Enumerated
    private ERelationship type;

    public Relationship() {}

    public Relationship(Developer originator, Developer recipient, ERelationship type) {
        this.originator = originator;
        this.recipient = recipient;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Developer getOriginator() {
        return originator;
    }

    public void setOriginator(Developer originator) {
        this.originator = originator;
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
