package com.demo.main.web;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.demo.main.domain.Plan;
import com.demo.main.service.PlanService;

@RestController
@RequestMapping
@RequestScope
public class PlanController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final PlanService planService;

	@Autowired
	public PlanController(PlanService planService) {
		this.planService = planService;
	}

	@GetMapping("/plans/{id}")
	public Optional<Plan> getPlansById(@PathVariable long id) {
		LOG.info("Getting Plan with ID {}.", id);
		return planService.getPlanById(id);
	}
	@GetMapping("/plans")
	public List<Plan> getAllPlans() {
		LOG.info("Getting All Plans");
		return planService.getAllPlans();
	}
	

}
