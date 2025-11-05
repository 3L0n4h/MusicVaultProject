package com.elonah.musicvault.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elonah.musicvault.models.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {

}
