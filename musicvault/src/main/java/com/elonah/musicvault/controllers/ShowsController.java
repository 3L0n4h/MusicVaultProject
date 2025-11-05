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

import com.elonah.musicvault.dto.ShowsDTO;
import com.elonah.musicvault.dto.ShowsDTOEdit;
import com.elonah.musicvault.models.Artist;
import com.elonah.musicvault.models.Shows;
import com.elonah.musicvault.service.ArtistService;
import com.elonah.musicvault.service.ShowsService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/shows")
public class ShowsController {

	private final ShowsService showsService;
	private final ArtistService artistService;

	public ShowsController(ShowsService showsService, ArtistService artistService) {
		this.showsService = showsService;
		this.artistService = artistService;
	}

	@GetMapping({ "", "/" })
	public String displayShowsList(Model model) {

		List<Shows> list_show = showsService.getAllShows();
		model.addAttribute("shows", list_show);

		return "shows/indexShow";
	}

	@GetMapping({ "/new" })
	public String showAddShows(Model model) {

		ShowsDTO showsDTO = new ShowsDTO();
		model.addAttribute("showsDTO", showsDTO);
		model.addAttribute("artists", artistService.getAllArtists());
		return "shows/PageNewShows";
	}

	@PostMapping({ "/new" })
	public String addShows(@Valid @ModelAttribute ShowsDTO showsDTO, BindingResult result) {
		if (result.hasErrors()) {
			return "shows/PageNewShows";
		}
		
		Shows show = new Shows();
		Artist artist = artistService.getArtistById(showsDTO.getArtistId())
				.orElseThrow(() -> new RuntimeException("Artist not found"));
		
		show.setArtist(artist);

		show.setEventName(showsDTO.getEventName());
		show.setEventType(showsDTO.getEventType());
		show.setVenue(showsDTO.getVenue());
		show.setEventDateTime(showsDTO.getEventDateTime());

		System.out.println("Debug here: " + showsDTO.getEventDateTime());

		showsService.saveShows(show);
		return "redirect:/shows";
	}

	@GetMapping({ "/edit" })
	public String showEditShows(Model model, @RequestParam int id) {

		try {

			Shows show = showsService.getShowsById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid Id: " + id));

			model.addAttribute("show", show);

			ShowsDTO showsDTO = new ShowsDTO();
			showsDTO.setEventId(show.getEventId());
			showsDTO.setArtistId(show.getArtist().getGroupId());
			Optional<Artist> artistOpt = artistService.getArtistById(showsDTO.getArtistId());
					
			if (artistOpt.isPresent()) {
				Artist artist = artistOpt.get();
				showsDTO.setArtistName(artist.getGroupName());
				System.out.println("Debugging artist: " + artist.getGroupName());
			} else {
				showsDTO.setArtistName("Not Found");
			}
			
			showsDTO.setEventName(show.getEventName());
			showsDTO.setEventType(show.getEventType());
			showsDTO.setVenue(show.getVenue());
			showsDTO.setEventDateTime(show.getEventDateTime());

			model.addAttribute("showsDTO", showsDTO); // make it accessible to page

		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			return "redirect:/shows";
		}
		return "shows/PageEditShows";
	}

	@PostMapping({ "/edit" })
	public String updateShow(Model model, @RequestParam int id,
			@Valid @ModelAttribute("showsDTO") ShowsDTOEdit showsDTO,
			BindingResult result) {


		try {
			Shows show = showsService.getShowsById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid shows Id: " + id));
			model.addAttribute("show", show);

			if (result.hasErrors()) {
				System.out.println("Result have errors");
				return "shows/PageEditShows";
			}


			show.setEventName(showsDTO.getEventName());
			System.out.println("Dev Debug: " + showsDTO.getEventName());
			show.setEventType(showsDTO.getEventType());
			show.setVenue(showsDTO.getVenue());
			show.setEventDateTime(showsDTO.getEventDateTime());
			showsService.saveShows(show);


		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}

		return "redirect:/shows";
	}

	@GetMapping("/delete/{id}")
	public String deleteShow(@PathVariable int id) {
		try {
			System.out.println("The id is:" + id);
			showsService.deleteShowsById(id);

		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}

		return "redirect:/shows";
	}

}
