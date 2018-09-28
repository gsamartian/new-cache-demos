package com.demo.main.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.demo.main.domain.Plan;
import com.demo.main.repository.PlanRepository;

@Service
public class PlanServiceImpl implements PlanService {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final PlanRepository planRepository;

	@Autowired
	public PlanServiceImpl(PlanRepository planRepository) {
		this.planRepository = planRepository;
	}

	
	
	@Override
	@Cacheable(value = "plans", key = "#id")
	public Optional<Plan> getPlanById(long id) {
		LOG.info("Getting Plan  with ID {}.", id);
		return planRepository.findById(id);
	}



	@Override
	public List<Plan> getAllPlans() {
		LOG.info("Getting All Plans");
		return planRepository.findAll();
	}
	
	
	

}
