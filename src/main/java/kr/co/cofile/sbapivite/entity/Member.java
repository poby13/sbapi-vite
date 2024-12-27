package kr.co.cofile.sbapivite.entity;

import kr.co.cofile.sbapivite.enums.MemberRole;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    private Long id;
    private String email;
    private String pw;
    private String nickname;
    private boolean social;
    private boolean enabled;

    @Builder.Default
    private List<MemberRole> memberRoles = new ArrayList<>();

    public void addRole(MemberRole role) {
        memberRoles.add(role);
    }
}
