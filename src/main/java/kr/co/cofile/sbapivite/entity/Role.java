package kr.co.cofile.sbapivite.entity;

import kr.co.cofile.sbapivite.enums.MemberRole;
import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    private Long id;
    private MemberRole roleName;

}
