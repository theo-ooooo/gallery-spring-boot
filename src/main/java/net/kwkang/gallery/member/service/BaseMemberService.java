package net.kwkang.gallery.member.service;

import lombok.RequiredArgsConstructor;
import net.kwkang.gallery.common.utill.HashUtils;
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
        String loginPwSalt = HashUtils.generateSalt(16);
        String loginPwSalted = HashUtils.generateHash(loginPw, loginPwSalt);
        memberRepository.save(new Member(name, loginId, loginPwSalted, loginPwSalt));
    }

    @Override
    public Member find(String loginId, String loginPw) {
        Optional<Member> memberOptional = memberRepository.findByLoginId(loginId);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();

            String loginPwSalt = memberOptional.get().getLoginPwSalt();

            String loginPwSalted = HashUtils.generateHash(loginPw, loginPwSalt);

            if(member.getLoginPw().equals(loginPwSalted)) {
                return member;
            }
        }

        return null;
    }

    @Override
    public Member find(String loginId) {
        return memberRepository.findByLoginId(loginId).orElse(null);
    }
}
