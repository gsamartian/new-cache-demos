package com.demo.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.demo.main.domain.Member;
import com.demo.main.domain.Plan;
import com.demo.main.domain.User;
import com.demo.main.repository.MemberRepository;
import com.demo.main.repository.PlanRepository;
import com.demo.main.repository.UserRepository;

@SpringBootApplication
@EnableCaching
public class CacheDemoApplication implements CommandLineRunner {
	
	

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final UserRepository userRepository;

	private final PlanRepository planRepository;
	
	private final MemberRepository memberRepository;
	


	@Autowired
	public CacheDemoApplication(UserRepository userRepository,PlanRepository planRepository,MemberRepository memberRepository) {
		this.userRepository = userRepository;
		this.planRepository=planRepository;
		this.memberRepository=memberRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(CacheDemoApplication.class, args);

		
	}

	@Override
	public void run(String... strings) {
		LOG.info("Saving users. Current user count is {}.", userRepository.count());
		User raj = new User("Raj", 5000);
		User sheldon = new User("Sheldon", 5000);
		User penny = new User("Penny", 0);
		User mary = new User("mary", 250);
		User barrow = new User("barrow", 250);
		User nancy = new User("nancy", 0);

		userRepository.deleteAll();
		userRepository.save(raj);
		userRepository.save(sheldon);
		userRepository.save(penny);
		userRepository.save(mary);
		userRepository.save(barrow);
		userRepository.save(nancy);
		LOG.info("Saving users. Current user count is {}.", userRepository.count());
		/*
		cacheMgr.getCache("users").put(raj.getId()+"", raj);
		cacheMgr.getCache("users").put(sheldon.getId()+"", sheldon);
		cacheMgr.getCache("users").put(penny.getId()+"", penny);
		cacheMgr.getCache("users").put(mary.getId()+"", mary);
		cacheMgr.getCache("users").put(barrow.getId()+"", barrow);
		cacheMgr.getCache("users").put(barrow.getId()+"", nancy);*/
		
		Plan seniorPlan=new Plan("senior","Plan for Seniors");
		Plan juniorPlan=new Plan("junior","Plan for Juniors");
		Plan groupPlan=new Plan("group","Plan for Groups");
		
		planRepository.deleteAll();
		planRepository.save(seniorPlan);
		planRepository.save(juniorPlan);
		planRepository.save(groupPlan);
		
		
		Member rajMember=new Member("Raj","GOLD");
		Member gopalMember=new Member("Gopal","SILVER");
		
		memberRepository.deleteAllInBatch();
		memberRepository.save(rajMember);
		memberRepository.save(gopalMember);
	}
	
}
