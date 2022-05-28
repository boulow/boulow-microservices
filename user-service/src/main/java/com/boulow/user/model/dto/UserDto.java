package com.boulow.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import com.boulow.user.model.Address;
import com.boulow.user.model.Gender;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private String bio;
    private String avatarUrl;
    private boolean isActive;
    private String firstName;
    private String lastName;
    private String langKey;
    private Gender gender;
    private Date dob;
    private List<Address> addresses;
    private String phone;
    private String uid;
    private String issuer;

}
