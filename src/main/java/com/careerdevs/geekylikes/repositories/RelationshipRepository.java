package com.careerdevs.geekylikes.repositories;

import com.careerdevs.geekylikes.entities.relationship.ERelationship;
import com.careerdevs.geekylikes.entities.relationship.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

    List<Relationship> findAllByOriginator_id(Long id);
//    List<Relationship> findAllByRecipient_id(Long id);
    List<Relationship> findAllByRecipient_idAndType(Long id, ERelationship type);
    Optional<Relationship> findByOriginator_idAndRecipient_id(Long oId, Long rId);
    // List<Relationship> findAllByOriginator_idOrRecipient_id(Long oId, Long rId);
}
