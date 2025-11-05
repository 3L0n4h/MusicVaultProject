package com.elonah.musicvault.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.elonah.musicvault.models.Artist;
import com.elonah.musicvault.repo.ArtistRepository;

@Service
public class ArtistService {

	private final ArtistRepository artistRepository;

	public ArtistService(ArtistRepository artistRepository) {
		this.artistRepository = artistRepository;
	}

	public List<Artist> getAllArtists() {
		return artistRepository.findAll();
	}

	public Optional<Artist> getArtistById(int id) {
		return artistRepository.findById(id);
	}

	public Artist saveArtist(Artist artist) {
		return artistRepository.save(artist);
	}

	public void deleteArtistById(int id) {
		artistRepository.deleteById(id);
	}

}
