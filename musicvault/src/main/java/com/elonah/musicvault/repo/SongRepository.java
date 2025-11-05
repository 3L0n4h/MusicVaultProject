package com.elonah.musicvault.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elonah.musicvault.models.Song;

public interface SongRepository extends JpaRepository<Song, Integer> {

	Optional<Song> findByTitleIgnoreCase(String title);
}
