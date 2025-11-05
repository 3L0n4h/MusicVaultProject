package com.elonah.musicvault.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.elonah.musicvault.models.Song;
import com.elonah.musicvault.repo.SongRepository;

@Service
public class SongService {

	private final SongRepository songRepository;

	public SongService(SongRepository songRepository) {
		this.songRepository = songRepository;
	}

	public List<Song> getAllSongs() {
		return songRepository.findAll();
	}

	public Optional<Song> getSongById(int id) {
		return songRepository.findById(id);
	}

	public Song saveSong(Song song) {
		return songRepository.save(song);
	}

	public void deleteSongById(int id) {
		songRepository.deleteById(id);
	}

	public Optional<Song> findSongByTitle(String title) {
		return songRepository.findByTitleIgnoreCase(title);
	}

}
