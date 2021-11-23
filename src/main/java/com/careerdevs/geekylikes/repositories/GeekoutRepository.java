package com.careerdevs.geekylikes.repositories;


import com.careerdevs.geekylikes.entities.geekout.Geekout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeekoutRepository extends JpaRepository<Geekout, Long> {

   List<Geekout> findByDeveloperId(Long id);

   //get a list of Geekouts liked by a developer
   List<Geekout> findAllByApprovals_developer_id(Long id);
}

