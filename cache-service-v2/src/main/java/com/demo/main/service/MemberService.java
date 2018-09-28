package com.demo.main.service;

import java.util.List;
import java.util.Optional;

import com.demo.main.domain.Member;

public interface MemberService {

	Optional<Member> getMemberById(long id);
	List<Member> getAllMembers();
}
