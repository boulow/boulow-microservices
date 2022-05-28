package com.boulow.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSlimDto {

	private Long id;
    private String email;
    private String bio;
    private String avatarUrl;
    private boolean isActive;
    private String firstName;
    private String lastName;
    private String langKey;
    
}
