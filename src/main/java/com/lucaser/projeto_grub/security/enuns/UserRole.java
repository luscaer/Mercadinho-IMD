package com.lucaser.projeto_grub.security.enuns;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("admin"),
    USER("usuario");

    private String role;

    UserRole(String role){
        this.role = role;
    }

}
