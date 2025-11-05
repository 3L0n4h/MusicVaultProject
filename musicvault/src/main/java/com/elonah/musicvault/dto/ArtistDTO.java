package com.elonah.musicvault.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

//Data Transfer Object Model
public class ArtistDTO {

	private Integer groupId;

	@NotEmpty(message = "Required field!")
	private String groupName;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Required field!")
	private LocalDate debutDate;

	@NotEmpty(message = "Required field!")
	private String agency;

	@NotEmpty(message = "Required field!")
	private String genre;

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public LocalDate getDebutDate() {
		return debutDate;
	}

	public void setDebutDate(LocalDate debutDate) {
		this.debutDate = debutDate;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

}
