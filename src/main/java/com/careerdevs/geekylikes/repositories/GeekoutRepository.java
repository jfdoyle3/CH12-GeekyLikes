package com.careerdevs.geekylikes.repositories;


import com.careerdevs.geekylikes.entities.Geekout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeekoutRepository extends JpaRepository<Geekout, Long> {

    List<Geekout> findByDeveloperId(Long id);
}

