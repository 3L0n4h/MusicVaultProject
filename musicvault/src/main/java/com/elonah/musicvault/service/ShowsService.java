package com.elonah.musicvault.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.elonah.musicvault.models.Shows;
import com.elonah.musicvault.repo.ShowsRepository;

@Service
public class ShowsService {

	private final ShowsRepository showsRepository;

	public ShowsService(ShowsRepository showsRepository) {
		this.showsRepository = showsRepository;
	}

	public List<Shows> getAllShows() {
		return showsRepository.findAll();
	}

	public Optional<Shows> getShowsById(int id) {
		return showsRepository.findById(id);
	}

	public Shows saveShows(Shows shows) {
		return showsRepository.save(shows);
	}

	public void deleteShowsById(int id) {
		showsRepository.deleteById(id);
	}

}
