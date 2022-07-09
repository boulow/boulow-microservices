package com.boulow.user.controller;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.boulow.user.mapper.UserMapper;
import com.boulow.user.model.AppUser;
import com.boulow.user.model.dto.UserDto;
import com.boulow.user.model.dto.UserUpdateDto;
import com.boulow.user.service.UserService;

@RestController
@RequestMapping(value = "v1/usr")
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserService userService;
	
	@PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public UserDto updateUser(@ModelAttribute UserUpdateDto toUser,
			@RequestHeader(value = "Accept-Language", required = false) Locale locale) {
        log.info("Inside updateUser method of UserController");
        return userMapper.convertToDto(userService.update(toUser, locale));
    }
    
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto signUp(@AuthenticationPrincipal AppUser user,
			@RequestHeader(value = "Accept-Language", required = false) Locale locale) {
        log.info("Inside signUp method of UserController");
        UserDto userDto = new UserDto();
        AppUser tmp = userService.findByEmail(user.getEmail()).orElse(null);
        if(tmp == null)
        	userDto = userMapper.convertToDto(userService.save(user));
        else
        	userDto = userMapper.convertToDto(tmp);
        return userDto;
    }

    @GetMapping
    public List<UserDto> getAllUsers(
			@RequestHeader(value = "Accept-Language", required = false) Locale locale) {
        log.info("Inside getAllUsers method of UserController");
        return userService.findAll().stream().map(user -> userMapper.convertToDto(user)).collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public UserDto findByUserId(@PathVariable("userId") Long userId,
			@RequestHeader(value = "Accept-Language", required = false) Locale locale) {
        log.info("Inside findByUserId method of UserController");
        return userMapper.convertToDto(userService.findById(userId, locale));
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId,
			@RequestHeader(value = "Accept-Language", required = false) Locale locale) {
        userService.delete(userId);
        return "User with ID " + userId + " was deleted successfully!";
    }

}
