package com.elonah.musicvault.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elonah.musicvault.models.Member;

public interface MembersRepository extends JpaRepository<Member, Integer> {

	List<Member> findMembersByArtist_GroupId(int groupId);
}
