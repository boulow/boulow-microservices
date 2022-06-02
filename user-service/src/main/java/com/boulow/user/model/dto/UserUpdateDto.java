package com.boulow.user.model.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.boulow.user.model.Address;
import com.boulow.user.model.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

	private String firstName;
    private String lastName;
    private String email;
    private String bio;
    private Gender gender;
    private String phone;
    private LocalDate dob;
    private String langKey;
    private MultipartFile avatar;
    private Long id;
    private List<Address> address;
}
