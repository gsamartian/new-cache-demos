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

import com.demo.main.domain.Member;
import com.demo.main.service.MemberService;

@RestController
@RequestMapping
@RequestScope
public class MemberController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final MemberService memberService;

	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping("/members/{id}")
	public Optional<Member> getMemberById(@PathVariable long id) {
		LOG.info("Getting Member with ID {}.", id);
		return memberService.getMemberById(id);
	}
	
	@GetMapping("/members")
	public List<?> getAllMembers() {
		LOG.info("Getting All Members");
		return memberService.getAllMembers();
	}

	

}
