package kr.co.cofile.sbapivite.dto;

import lombok.Data;

@Data
public class AuthResponse {

    private String token;

    private String refreshToken;

    private Long expiration;

}
