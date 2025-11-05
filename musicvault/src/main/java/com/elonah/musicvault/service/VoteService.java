package com.elonah.musicvault.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.elonah.musicvault.models.Song;
import com.elonah.musicvault.models.Vote;
import com.elonah.musicvault.repo.VoteRepository;

@Service
public class VoteService {

	private final VoteRepository voteRepository;

	public VoteService(VoteRepository voteRepository) {
		this.voteRepository = voteRepository;
	}

	public List<Vote> getAllVote() {
		return voteRepository.findAll();
	}

	public Optional<Vote> getVoteById(int id) {
		return voteRepository.findById(id);
	}

	public Vote saveVote(Vote vote) {
		return voteRepository.save(vote);
	}

	public void deleteVoteById(int id) {
		voteRepository.deleteById(id);
	}

	public List<Song> getTopSongsThisMonth() {
		LocalDate now = LocalDate.now();
		return voteRepository.findTopSongs(now.getYear(), now.getMonthValue());
	}

	public Optional<Vote> findBySongAndMonthYear(Song song, LocalDate monthYear) {
		return voteRepository.findBySongAndMonthYear(song, monthYear);
	}
}
