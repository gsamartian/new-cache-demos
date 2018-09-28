package com.demo.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.main.domain.Plan;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
}
