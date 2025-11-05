package com.elonah.musicvault.dto;

import java.time.LocalDate;

public class VoteDTO {

	private Integer voteId;

	private Integer songId; // FK

	private Integer voteCount;

	private LocalDate monthYear;

	private String songTitle;

	public Integer getVoteId() {
		return voteId;
	}

	public void setVoteId(Integer voteId) {
		this.voteId = voteId;
	}

	public Integer getSongId() {
		return songId;
	}

	public void setSongId(Integer songId) {
		this.songId = songId;
	}

	public Integer getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(Integer voteCount) {
		this.voteCount = voteCount;
	}

	public LocalDate getMonthYear() {
		return monthYear;
	}

	public void setMonthYear(LocalDate monthYear) {
		this.monthYear = monthYear;
	}

	public String getSongTitle() {
		return songTitle;
	}

	public void setSongTitle(String songTitle) {
		this.songTitle = songTitle;
	}

}
