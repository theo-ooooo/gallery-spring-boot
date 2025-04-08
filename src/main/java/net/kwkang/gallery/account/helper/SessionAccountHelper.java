package net.kwkang.gallery.account.helper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.kwkang.gallery.account.dto.AccountJoinRequest;
import net.kwkang.gallery.account.dto.AccountLoginRequest;
import net.kwkang.gallery.account.etc.AccountConstants;
import net.kwkang.gallery.common.utill.HttpUtils;
import net.kwkang.gallery.member.entity.Member;
import net.kwkang.gallery.member.service.MemberService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionAccountHelper implements AccountHelper {
    private final MemberService memberService;

    @Override
    public void join(AccountJoinRequest joinReq) {
        memberService.save(joinReq.getName(), joinReq.getLoginId(), joinReq.getLoginPw());
    }

    @Override
    public String login(AccountLoginRequest loginReq, HttpServletRequest req, HttpServletResponse res) {
        Member member = memberService.find(loginReq.getLoginId(), loginReq.getLoginPw());

        if(member == null) {
            return null;
        }
        HttpUtils.setSession(req, AccountConstants.MEMBER_ID_NAME, member.getId());
        return member.getLoginId();
    }

    @Override
    public Integer getMemberId(HttpServletRequest req) {
        Object memberId = HttpUtils.getSessionValue(req, AccountConstants.MEMBER_ID_NAME);

        if(memberId != null) {
            return (int) memberId;
        }
        return null;
    }

    @Override
    public boolean isLoggedIn(HttpServletRequest req) {
        return getMemberId(req) != null;
    }

    @Override
    public void logout(HttpServletRequest req, HttpServletResponse res) {
        HttpUtils.removeSession(req, AccountConstants.MEMBER_ID_NAME);
    }
}
