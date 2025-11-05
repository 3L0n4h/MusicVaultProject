package com.elonah.musicvault.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.elonah.musicvault.dto.ArtistDTO;
import com.elonah.musicvault.models.Artist;
import com.elonah.musicvault.models.Member;
import com.elonah.musicvault.service.ArtistService;
import com.elonah.musicvault.service.MembersService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/artist")
public class ArtistController {


	private final ArtistService artistService;
	private final MembersService membersService;

	public ArtistController(ArtistService artistService, MembersService membersService) {
		this.artistService = artistService;
		this.membersService = membersService;
	}


	@GetMapping({ "", "/" })
	public String showProductList(Model model) {

		List<Artist> list_artist = artistService.getAllArtists();
		model.addAttribute("artist", list_artist);

		return "artist/indexArtist"; // folder name in resources templates
	}

	@GetMapping({ "/new" })
	public String showAddArtist(Model model) {
		ArtistDTO artistDTO = new ArtistDTO();
		model.addAttribute("artistDTO", artistDTO);
		return "artist/PageNewArtist";
	}

	@PostMapping({ "/new" })
	public String addArtist(@Valid @ModelAttribute ArtistDTO artistDTO, BindingResult result) {
		// binding result is for validation error check

		if (result.hasErrors()) {
			return "artist/PageNewArtist";
		}

		Artist artist = new Artist();
		artist.setGroupName(artistDTO.getGroupName());
		artist.setDebutDate(artistDTO.getDebutDate());
		artist.setAgency(artistDTO.getAgency());
		artist.setGenre(artistDTO.getGenre());

		artistService.saveArtist(artist);
		// repo.save(artist);

		return "redirect:/artist";
	}

	@GetMapping({ "/edit" })
	public String showEditArtist(Model model, @RequestParam int id) {

		try {

			Artist artist = artistService.getArtistById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid artist Id: " + id));

			model.addAttribute("artist", artist);

			ArtistDTO artistDTO = new ArtistDTO();
			artistDTO.setGroupName(artist.getGroupName());
			artistDTO.setDebutDate(artist.getDebutDate());
			artistDTO.setAgency(artist.getAgency());
			artistDTO.setGenre(artist.getGenre());

			model.addAttribute("artistDTO", artistDTO); // make it accessible to page

		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			return "redirect:/artist";
		}
		return "artist/PageEditArtist";
	}

	@PostMapping({ "/edit" })
	public String updateArtist(Model model, @RequestParam int id, @Valid @ModelAttribute ArtistDTO artistDTO,
			BindingResult result) {

		try {
			Artist artist = artistService.getArtistById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid artist Id: " + id));
				model.addAttribute("artist",artist);
				
				if(result.hasErrors()) {
					return "artist/PageEditArtist";
				}
					
				artist.setGroupName(artistDTO.getGroupName());
				artist.setDebutDate(artistDTO.getDebutDate());
				artist.setAgency(artistDTO.getAgency());
				artist.setGenre(artistDTO.getGenre());
				
				
				artistService.saveArtist(artist);

		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}

		return "redirect:/artist";
	}

	@GetMapping("/delete/{id}")
	public String deleteArtist(@PathVariable int id) {
		try {
			System.out.println("The id is:" + id);
			artistService.deleteArtistById(id);

		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}

		return "redirect:/artist";
	}

	@GetMapping("/members")
	public String manageMembers(@RequestParam("id") int groupId, Model model) { // url param convert to int, store to
																				// groupID
		// pass group ID so that I can access it.
		// group ID and groupName

		System.out.println("dev debug: test");
		Artist artist = artistService.getArtistById(groupId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + groupId));

		List<Member> members = membersService.getMembersByArtistId(groupId);

	    // Add data to the model
	    model.addAttribute("artist", artist);
		model.addAttribute("members", members);

		return "member/indexMember";
}
	

}
