package com.demo.main.service;

import java.util.List;
import java.util.Optional;

import com.demo.main.domain.Plan;

public interface PlanService {

	Optional<Plan> getPlanById(long id);
	List<Plan> getAllPlans();
}
