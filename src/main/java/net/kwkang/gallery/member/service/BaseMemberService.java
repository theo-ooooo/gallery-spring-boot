package net.kwkang.gallery.member.service;

import lombok.RequiredArgsConstructor;
import net.kwkang.gallery.member.entity.Member;
import net.kwkang.gallery.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseMemberService implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public void save(String name, String loginId, String loginPw) {
        memberRepository.save(new Member(name, loginId, loginPw));
    }

    @Override
    public Member find(String loginId, String loginPw) {
        Optional<Member> memberOptional = memberRepository.findByLoginIdAndLoginPw(loginId, loginPw);

        return memberOptional.orElse(null);
    }
}
