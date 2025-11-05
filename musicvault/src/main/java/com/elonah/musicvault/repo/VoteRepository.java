package com.elonah.musicvault.repo;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elonah.musicvault.models.Song;
import com.elonah.musicvault.models.Vote;

public interface VoteRepository extends JpaRepository<Vote, Integer> {

	Optional<Vote> findBySongAndMonthYear(Song song, LocalDate monthYear);

	@Query("SELECT v.song " + "FROM Vote v " + "WHERE YEAR(v.monthYear) = :year AND MONTH(v.monthYear) = :month "
			+ "ORDER BY v.voteCount DESC")
	List<Song> findTopSongs(@Param("year") int year, @Param("month") int month);

}
