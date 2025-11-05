package com.elonah.musicvault.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.elonah.musicvault.models.Member;
import com.elonah.musicvault.repo.MembersRepository;

@Service
public class MembersService {
	private final MembersRepository membersRepository;

	public MembersService(MembersRepository membersRepository) {
		this.membersRepository = membersRepository;
	}

	public List<Member> getAllMembers() {
		return membersRepository.findAll();
	}

	public Optional<Member> getMemberById(int id) {
		return membersRepository.findById(id);
	}

	public Member saveMember(Member member) {
		return membersRepository.save(member);
	}

	public void deleteMemberById(int id) {
		membersRepository.deleteById(id);
	}

	public List<Member> getMembersByArtistId(int groupId) {
		return membersRepository.findMembersByArtist_GroupId(groupId);
	}

}
