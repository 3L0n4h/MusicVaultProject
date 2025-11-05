package com.elonah.musicvault.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.elonah.musicvault.dto.VoteDTO;
import com.elonah.musicvault.models.Song;
import com.elonah.musicvault.models.Vote;
import com.elonah.musicvault.service.SongService;
import com.elonah.musicvault.service.VoteService;

import jakarta.validation.Valid;

@Controller
public class HomeController {

	private final VoteService voteService;
	private final SongService songService;

	public HomeController(VoteService voteService, SongService songService) {
		this.voteService = voteService;
		this.songService = songService;
	}

	@GetMapping("/")
	public String Home(Model model) {
		VoteDTO voteDTO = new VoteDTO();
		
		model.addAttribute("voteTop", voteService.getTopSongsThisMonth());
		List<Song> debug_getTop = voteService.getTopSongsThisMonth();
		for (Song s : debug_getTop) {
			System.out.println(s.getTitle());
		}

		model.addAttribute("voteDTO", voteDTO);
		model.addAttribute("songs", songService.getAllSongs());
		return "home/indexHome";
	}

	@PostMapping("/newVote")
	public String submitVote(@Valid @ModelAttribute VoteDTO voteDTO, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {

			System.out.println("test debug: has error");
			result.getAllErrors().forEach(error -> {
				System.out.println("Validation error: " + error.getDefaultMessage());
			});
			return "home/indexHome";
		}

		Song songTitle = songService.findSongByTitle(voteDTO.getSongTitle())
				.orElseThrow(() -> new RuntimeException("Song title not found"));
		voteDTO.setSongId(songTitle.getSongId());

		System.out.println("test debug: " + songTitle.getTitle() + " " + songTitle.getSongId());

		LocalDate currentMonthYear = LocalDate.now();

		Optional<Vote> existSongVote = voteService.findBySongAndMonthYear(songTitle, currentMonthYear);

		if (existSongVote.isPresent()) {
			Vote vote = existSongVote.get();
			vote.setVoteCount(vote.getVoteCount() + 1);
			voteService.saveVote(vote);
			System.out.println("test debug: Exist");
		} else {
			Vote vote = new Vote();
			vote.setSong(songTitle);
			vote.setMonthYear(currentMonthYear);
			vote.setVoteCount(1);
			voteService.saveVote(vote);
			System.out.println("test debug: not Exist");
		}

		redirectAttributes.addFlashAttribute("successMessage",
				"Vote submitted for \"" + songTitle.getTitle() + "\"");

		return "redirect:/";
	}

	@GetMapping("/about")
	public String about() {
		return "home/about";
	}

}
