package com.demo.main.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.demo.main.domain.Member;
import com.demo.main.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final MemberRepository memberRepository;

	@Autowired
	public MemberServiceImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	
	
	@Override
	@Cacheable(value = "members", key = "#id")
	public Optional<Member> getMemberById(long id) {
		LOG.info("Getting Member with ID {}.", id);
		return memberRepository.findById(id);
	}



	@Override
	public List<Member> getAllMembers() {
		LOG.info("Fetching All Members");
		return memberRepository.findAll();
	}
	
	
	

}
