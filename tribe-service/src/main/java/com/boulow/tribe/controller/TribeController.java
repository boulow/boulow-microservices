package com.boulow.tribe.controller;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boulow.tribe.mapper.MemberMapper;
import com.boulow.tribe.mapper.TribeMapper;
import com.boulow.tribe.model.Tribe;
import com.boulow.tribe.model.dto.MemberDto;
import com.boulow.tribe.model.dto.MembershipDto;
import com.boulow.tribe.model.dto.TribeDto;
import com.boulow.tribe.service.TribeService;

@RestController
@RequestMapping(value = "v1/tribe")
public class TribeController {

	@Autowired
	private TribeService tribeService;

	@Autowired
	private TribeMapper tribeMapper;

	@Autowired
	private MemberMapper memberMapper;

	static final Logger logger = LoggerFactory.getLogger(TribeController.class);

	@GetMapping(value = "/")
	public ResponseEntity<List<Tribe>> getAllTribes(
			@RequestHeader(value = "Accept-Language", required = false) Locale locale) {
		List<Tribe> tribesList = tribeService.findAll(locale);
		return ResponseEntity.ok(tribesList);
	}

	@GetMapping(value = "/memberships/{userId}")
	public ResponseEntity<List<TribeDto>> getMyTribes(@PathVariable("userId") Long userId,
			@RequestHeader(value = "Accept-Language", required = false) Locale locale) {
		List<TribeDto> tribesList = tribeService.findMyTribes(userId, locale).stream()
				.map(tribe -> tribeMapper.convertToDto(tribe)).collect(Collectors.toList());
		return ResponseEntity.ok(tribesList);
	}

	@PutMapping
	public ResponseEntity<TribeDto> updateTribe(@RequestBody TribeDto tribeDto,
			@RequestHeader(value = "Accept-Language", required = false) Locale locale) {
		return ResponseEntity
				.ok(tribeMapper.convertToDto(tribeService.update(tribeMapper.convertFromDto(tribeDto), locale)));
	}

	@PostMapping
	public ResponseEntity<TribeDto> createTribe(@RequestBody TribeDto tribeDto,
			@RequestHeader(value = "Accept-Language", required = false) Locale locale) {
		return ResponseEntity.ok(tribeMapper.convertToDto(
				tribeService.create(tribeMapper.convertFromDto(tribeDto), Long.valueOf(tribeDto.getUserId()), locale)));
	}

	@PostMapping(value = "/leave")
	public ResponseEntity<String> leaveTribe(@RequestBody MembershipDto membershipDto,
			@RequestHeader(value = "Accept-Language", required = false) Locale locale) {
		return ResponseEntity.ok(tribeService.leave(membershipDto.getUserId(), membershipDto.getTribeId(), locale));
	}

	@PostMapping(value = "/join")
	public ResponseEntity<MemberDto> joinTribe(@RequestBody MembershipDto membershipDto,
			@RequestHeader(value = "Accept-Language", required = false) Locale locale) {
		return ResponseEntity.ok(memberMapper
				.convertToDto(tribeService.join(membershipDto.getUserId(), membershipDto.getTribeId(), locale)));
	}

	@DeleteMapping(value = "/{tribeId}")
	public ResponseEntity<String> deleteTribe(@PathVariable("tribeId") Long tribeId,
			@RequestHeader(value = "Accept-Language", required = false) Locale locale) {
		return ResponseEntity.ok(tribeService.delete(tribeId, locale));
	}
}
