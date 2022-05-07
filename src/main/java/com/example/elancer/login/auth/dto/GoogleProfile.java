package com.example.elancer.login.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleProfile {
    private String sub;
    private String name;
    private String email;
    private String picture;
}
