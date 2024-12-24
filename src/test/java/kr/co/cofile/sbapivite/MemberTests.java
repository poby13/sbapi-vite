package kr.co.cofile.sbapivite;

import kr.co.cofile.sbapivite.dto.Member;
import kr.co.cofile.sbapivite.mapper.MemberMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MemberTests {

    @Autowired
    private MemberMapper memberMapper;

    @Test
    @Transactional
    public void testMamberSignUp() {
        // 회원정보
        Member member = new Member();
        member.setEmail("test@email.com");
        member.setPw("pw123");
        member.setNickname("test");
        member.setSocial(false);
        member.setEnabled(true);

        //
    }

}
