package com.elonah.musicvault.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elonah.musicvault.models.Shows;

public interface ShowsRepository extends JpaRepository<Shows, Integer> {

}
