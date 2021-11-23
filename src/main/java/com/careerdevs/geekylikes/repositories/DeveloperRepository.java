package com.careerdevs.geekylikes.repositories;

import com.careerdevs.geekylikes.entities.developer.Developer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    List<Developer> findAllByCohort(Integer cohort, Sort sort);

    Developer findByAvatar_id(Long id);

    List<Developer> findAllBygeekout_id(Long id);

    //get a list of developers that liked geekout
    List<Developer> findAllByApprovals_geekout_id(Long id);
}
