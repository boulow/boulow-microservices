package com.boulow.tribe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boulow.tribe.model.Member;
import com.boulow.tribe.model.MemberRole;
import com.boulow.tribe.model.Tribe;
import com.boulow.tribe.model.dto.UserSlimDto;
import com.boulow.tribe.repository.MemberRepository;
import com.boulow.tribe.repository.TribeRepository;
import com.boulow.tribe.service.client.UserFeignClient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.bulkhead.annotation.Bulkhead.Type;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

import com.boulow.tribe.exception.InvalidArgumentException;
import com.boulow.tribe.exception.NoSuchElementFoundException;

@Service("tribeService")
@Transactional
public class TribeService {
	
	static final Logger logger = LoggerFactory.getLogger(TribeService.class);

	@Autowired
	MessageSource messages;

	@Autowired
	private TribeRepository tribeRepository;

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private UserFeignClient userFeignClient;

	public Tribe findById(Long tribeId, Locale locale) {
		Tribe tribe = tribeRepository.findById(tribeId).orElseThrow(() -> new NoSuchElementFoundException(
				String.format(messages.getMessage("tribe.absent", null, locale), tribeId)));
		return tribe;
	}

	public List<Tribe> findAll(Locale locale) {
		List<Tribe> tribesList = new ArrayList<>();
		tribeRepository.findAll().forEach(tribesList::add);
		return tribesList;
	}

	public List<Tribe> findMyTribes(Long userId, Locale locale) {
		if(callUserFeignClient(userId) == null)
			throw new IllegalArgumentException("User doesn't exist");
		List<Tribe> tribesList = new ArrayList<>();
		memberRepository.findByUserId(userId).forEach(member -> tribesList.add(member.getTribe()));
		return tribesList;
	}

	// TODO Implement getTribeMembers(tribeId)

	public String leave(Long userId, Long tribeId, Locale locale) {
		String responseMessage = null;
		// Validate TribeId
		Tribe tribe = tribeRepository.findById(tribeId).orElseThrow(() -> new NoSuchElementFoundException(
				String.format(messages.getMessage("tribe.absent", null, locale), tribeId)));
		Member member = memberRepository.findByUserIdAndTribe(userId, tribe);
		
		if(member == null)
			throw new NoSuchElementFoundException(
				String.format(messages.getMessage("member.absent", null, locale), ""));

		tribe.getMembers();
		tribe.removeMember(member);
		tribeRepository.save(tribe);
		memberRepository.delete(member);
		responseMessage = String.format(messages.getMessage("member.left.tribe", null, locale), member.getUserId(),
				tribe.getId());
		return responseMessage;
	}

	public Member join(Long tribeId, Long userId, Locale locale) {
		if (tribeId == null || userId == null)
			throw new InvalidArgumentException(messages.getMessage("tribe.invalid.argument", null, locale));

		// TODO Add userId validation via WebService call with Feign/RestTemplate

		// Validate TribeId
		Tribe tribe = tribeRepository.findById(tribeId).orElseThrow(() -> new NoSuchElementFoundException(
				String.format(messages.getMessage("tribe.absent", null, locale), tribeId)));

		// Creating Default Member
		Member member = new Member();
		member.setUserId(userId);
		member.setRole(MemberRole.DEFAULT);
		tribe.getMembers();
		tribe.addMember(member);
		tribeRepository.save(tribe);
		return member;
	}

	public Tribe create(Tribe tribe, Long userId, Locale locale) {
		if (tribe == null || userId == null)
			throw new InvalidArgumentException(messages.getMessage("tribe.invalid.argument", null, locale));

		// TODO Add userId validation via WebService call with Feign/RestTemplate

		// Creating Admin Member
		Member member = new Member();
		member.setUserId(userId);
		member.setRole(MemberRole.PRESIDENT);

		// Adding Default Member(admin) to Tribe
		tribe.addMember(member);

		// Persisting tribe
		return tribeRepository.save(tribe);
	}

	public Tribe update(Tribe tribe, Locale locale) {
		if (tribe != null) {
			tribeRepository.findById(tribe.getId()).orElseThrow(() -> new NoSuchElementFoundException(
					String.format(messages.getMessage("tribe.absent", null, locale), tribe.getId())));
			tribeRepository.save(tribe);
		} else
			throw new InvalidArgumentException(messages.getMessage("tribe.invalid.argument", null, locale));
		return tribe;
	}

	public String delete(Long tribeId, Locale locale) {
		String responseMessage = null;
		Tribe tribe = tribeRepository.findById(tribeId).orElseThrow(() -> new NoSuchElementFoundException(
				String.format(messages.getMessage("tribe.absent", null, locale), tribeId)));
		tribeRepository.delete(tribe);
		responseMessage = String.format(messages.getMessage("tribe.delete.message", null, locale), tribeId);
		return responseMessage;
	}
	
	@CircuitBreaker(name = "userService")
	@Bulkhead(name = "bulkheadUserService", type = Type.THREADPOOL)
	@Retry(name = "retryUserService")
	public UserSlimDto callUserFeignClient(Long userId) {
		return userFeignClient.getUser(userId);
	}
}
