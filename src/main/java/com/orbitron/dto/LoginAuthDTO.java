package com.orbitron.dto;

import java.util.Set;

import com.orbitron.entities.entities.Role;

import lombok.Getter;

@Getter
public class LoginAuthDTO {
    private final String appSessionToken;
    private final Set<Role> roles;

    public LoginAuthDTO(String appSessionToken, Set<Role> roles) {
        this.appSessionToken = appSessionToken;
        this.roles = roles;
    }
}
