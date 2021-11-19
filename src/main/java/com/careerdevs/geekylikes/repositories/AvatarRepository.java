package com.careerdevs.geekylikes.repositories;

import com.careerdevs.geekylikes.entities.avatar.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {}
