package com.careerdevs.geekylikes.repositories;

import com.careerdevs.geekylikes.entities.language.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, Long> {}
