package com.example.coursesite_final.history.repository;

import com.example.coursesite_final.history.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History,Long> {
    Optional<List<History>> findTop10ByUserIdOrderByLogDtDesc(String userId);
}