package kr.co.cofile.sbapivite.dto;

import lombok.Data;

@Data
public class Member {

    private Long id;

    private String email;

    private String pw;

    private String nickname;

    private boolean social;

    private boolean enabled;

}
