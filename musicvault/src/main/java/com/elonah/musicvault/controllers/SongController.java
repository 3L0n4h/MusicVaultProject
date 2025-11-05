package com.elonah.musicvault.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.elonah.musicvault.dto.SongDTO;
import com.elonah.musicvault.dto.SongDTOEdit;
import com.elonah.musicvault.models.Artist;
import com.elonah.musicvault.models.Song;
import com.elonah.musicvault.service.ArtistService;
import com.elonah.musicvault.service.SongService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/song")
public class SongController {

	private final SongService songService;
	private final ArtistService artistService;

	public SongController(SongService songService, ArtistService articeService) {
		this.songService = songService;
		this.artistService = articeService;
	}

	@GetMapping({ "", "/" })
	public String displayShowsList(Model model) {

		List<Song> list_song = songService.getAllSongs();
		model.addAttribute("song", list_song);

		return "song/indexSong"; // folder name in resources templates
	}

	@GetMapping({ "/new" })
	public String songAddSong(Model model) {

		SongDTO songDTO = new SongDTO();
		model.addAttribute("songDTO", songDTO);
		model.addAttribute("artists", artistService.getAllArtists());
		return "song/pageNewSong";
	}

	@PostMapping({ "/new" })
	public String addSong(@Valid @ModelAttribute SongDTO songDTO, BindingResult result) {

		if (result.hasErrors()) {
			return "song/PageNewSong";
		}

		Song song = new Song();
		Artist artist = artistService.getArtistById(songDTO.getArtistId())
				.orElseThrow(() -> new RuntimeException("Artist not found"));
		song.setArtist(artist);
		song.setComposer(songDTO.getComposer());
		song.setReleaseDate(songDTO.getReleaseDate());
		song.setTitle(songDTO.getTitle());

		songService.saveSong(song);
		return "redirect:/song";
	}

	@GetMapping({ "/edit" })
	public String showEditShows(Model model, @RequestParam int id) {

		try {

			Song song = songService.getSongById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid Id: " + id));

			model.addAttribute("song", song);

			SongDTO songDTO = new SongDTO();
			songDTO.setSongId(song.getSongId());
			songDTO.setArtistId(song.getArtist().getGroupId());
			Optional<Artist> artistOpt = artistService.getArtistById(songDTO.getArtistId());

			if (artistOpt.isPresent()) {
				Artist artist = artistOpt.get();
				songDTO.setArtistName(artist.getGroupName());
				System.out.println("Debugging artist: " + artist.getGroupName());
			} else {
				songDTO.setArtistName("Not Found");
			}

			songDTO.setComposer(song.getComposer());
			songDTO.setReleaseDate(song.getReleaseDate());
			songDTO.setTitle(song.getTitle());

			model.addAttribute("songDTO", songDTO);

		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			return "redirect:/song";
		}
		return "song/PageEditSong";
	}

	@PostMapping({ "/edit" })
	public String updateShow(Model model, @RequestParam int id,
			@Valid @ModelAttribute("songDTO") SongDTOEdit songDTO, BindingResult result) {

		try {
			Song song = songService.getSongById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid shows Id: " + id));
			model.addAttribute("song", song);

			if (result.hasErrors()) {
				System.out.println("Result have errors");
				return "song/PageEditSong";
			}

			song.setComposer(songDTO.getComposer());
			song.setReleaseDate(songDTO.getReleaseDate());
			song.setTitle(songDTO.getTitle());

			songService.saveSong(song);

		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}

		return "redirect:/song";
	}

	@GetMapping("/delete/{id}")
	public String deleteSong(@PathVariable int id) {
		System.out.println("The is id outside try:" + id);
		try {
			System.out.println("The id is:" + id);
			songService.deleteSongById(id);

		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}

		return "redirect:/song";
	}

}
