package com.careerdevs.geekylikes.repositories;


import com.careerdevs.geekylikes.entities.Geekout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeekoutRepository extends JpaRepository<Geekout, Long> {
}

