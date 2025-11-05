package com.elonah.musicvault.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.elonah.musicvault.dto.MemberDTO;
import com.elonah.musicvault.dto.MemberDTOEdit;
import com.elonah.musicvault.models.Artist;
import com.elonah.musicvault.models.Member;
import com.elonah.musicvault.service.ArtistService;
import com.elonah.musicvault.service.MembersService;
import com.elonah.musicvault.service.ShowsService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/member")
public class MembersController {

	private final ShowsService showsService;
	private final ArtistService artistService;
	private final MembersService membersService;

	public MembersController(ShowsService showsService, ArtistService artistService, MembersService membersService) {
		this.showsService = showsService;
		this.artistService = artistService;
		this.membersService = membersService;
	}

	@GetMapping({ "/new" })
	public String showAddShows(@RequestParam("id") int groupId, Model model) {

		
		MemberDTO memberDTO = new MemberDTO();
		Artist artist = artistService.getArtistById(groupId)
				.orElseThrow(() -> new RuntimeException("Artist not found"));

		memberDTO.setArtistId(groupId);
		memberDTO.setArtistName(artist.getGroupName());

		model.addAttribute("memberDTO", memberDTO);
		model.addAttribute("artists", artistService.getAllArtists());
		return "member/PageNewMember";
	}

	@PostMapping({ "/new" })
	public String addArtist(@RequestParam("id") int groupId, @Valid @ModelAttribute MemberDTO memberDTO,
			BindingResult result) {
		// binding result is for validation error check

		if (result.hasErrors()) {
			System.out.println("Error occured!");
			return "redirect:/member/new?id=" + groupId;
		}

		Artist artist = artistService.getArtistById(groupId)
				.orElseThrow(() -> new RuntimeException("Artist not found"));
		
		Member member = new Member();
		member.setArtist(artist);
		member.setStageName(memberDTO.getStageName());
		member.setFullName(memberDTO.getFullName());
		member.setPosition(memberDTO.getPosition());
		member.setBirthday(memberDTO.getBirthday());

		membersService.saveMember(member);

		// slash before artist, so that controller req. mapping won't append
		return "redirect:/artist/members?id=" + groupId;
	}

	@GetMapping({ "/edit" })
	public String showEditMember(Model model, @RequestParam int id) {

		try {

			Member member = membersService.getMemberById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid Id: " + id));

			model.addAttribute("member", member);

			MemberDTO membersDTO = new MemberDTO();
			membersDTO.setMemberId(member.getMemberId());
			membersDTO.setStageName(member.getStageName());
			membersDTO.setFullName(member.getFullName());
			membersDTO.setPosition(member.getPosition());
			membersDTO.setBirthday(member.getBirthday());
			membersDTO.setArtistName(member.getArtist().getGroupName());
			membersDTO.setArtistId(member.getArtist().getGroupId());
			System.out.println("artistID is: " + membersDTO.getArtistId());

			model.addAttribute("membersDTO", membersDTO); // make it accessible to page

		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			return "redirect:/artist/members?id=" + id;
		}

		return "member/PageEditMember";
	}

	@PostMapping({ "/edit" })
	public String updateMember(Model model, @RequestParam int id,
			@Valid @ModelAttribute("showsDTO") MemberDTOEdit membersDTO, BindingResult result) {

		try {
			Member member = membersService.getMemberById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid shows Id: " + id));
			model.addAttribute("member", member);

			if (result.hasErrors()) {
				System.out.println("Result have errors");
				return "redirect:/artist/members?id=" + id;
			}

			member.setStageName(membersDTO.getStageName());
			member.setFullName(membersDTO.getFullName());
			member.setPosition(membersDTO.getPosition());
			member.setBirthday(membersDTO.getBirthday());
			membersService.saveMember(member);

		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}

		return "redirect:/artist/members?id=" + id;
	}

	@GetMapping("/delete/{id}")
	public String deleteMember(@RequestParam("groupId") int groupId, @PathVariable int id) {
		try {
			System.out.println("The id is:" + id);
			membersService.deleteMemberById(id);

		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}

		return "redirect:/artist/members?id=" + groupId;
	}



}
