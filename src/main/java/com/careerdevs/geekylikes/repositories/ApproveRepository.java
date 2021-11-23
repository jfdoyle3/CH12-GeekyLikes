package com.careerdevs.geekylikes.repositories;

import com.careerdevs.geekylikes.entities.approve.Approve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApproveRepository extends JpaRepository<Approve, Long> {
}
