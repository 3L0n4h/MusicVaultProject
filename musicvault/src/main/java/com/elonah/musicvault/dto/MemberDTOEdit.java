package com.elonah.musicvault.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.elonah.musicvault.models.Artist;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class MemberDTOEdit {

	private int memberId;

	private Integer artistId; // FK

	private Artist artist;

	@NotEmpty(message = "Required field!")
	private String stageName;

	@NotEmpty(message = "Required field!")
	private String fullName;

	@NotEmpty(message = "Required field!")
	private String position;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Required field!")
	private LocalDate birthday;

	private String artistName;

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}


	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public Integer getArtistId() {
		return artistId;
	}

	public void setArtistId(Integer artistId) {
		this.artistId = artistId;
	}

}
