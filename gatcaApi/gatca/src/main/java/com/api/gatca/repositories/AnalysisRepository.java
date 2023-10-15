package com.api.gatca.repositories;

import com.api.gatca.models.entities.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisRepository extends JpaRepository<Analysis,Long> {
}
