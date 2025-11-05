package com.elonah.musicvault.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "VOTE")
public class Vote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VOTE_ID")
	private int voteId;

	@ManyToOne
	@JoinColumn(name = "SONG_ID", nullable = false)
	private Song song;

	@Column(name = "VOTE_COUNT")
	private int voteCount = 0;

	@Column(name = "MONTH_YEAR")
	private LocalDate monthYear;

	public int getVoteId() {
		return voteId;
	}

	public void setVoteId(int voteId) {
		this.voteId = voteId;
	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}

	public LocalDate getMonthYear() {
		return monthYear;
	}

	public void setMonthYear(LocalDate monthYear) {
		this.monthYear = monthYear;
	}

}
