package net.kwkang.gallery.account.helper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.kwkang.gallery.account.dto.AccountJoinRequest;
import net.kwkang.gallery.account.dto.AccountLoginRequest;
import net.kwkang.gallery.account.etc.AccountConstants;
import net.kwkang.gallery.block.service.BlockService;
import net.kwkang.gallery.common.utill.HttpUtils;
import net.kwkang.gallery.common.utill.TokenUtils;
import net.kwkang.gallery.member.entity.Member;
import net.kwkang.gallery.member.service.MemberService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Primary
@RequiredArgsConstructor
public class TokenAccountHelper implements AccountHelper {
    private  final MemberService memberService;
    private final BlockService blockService;

    private String getAccessToken(HttpServletRequest request) {
        return HttpUtils.getBearerToken(request);
    }

    private String getRefreshToken(HttpServletRequest request) {
        return HttpUtils.getCookie(request, AccountConstants.REFRESH_TOKEN_NAME);
    }

    private Integer getMemberId(String token) {
        if(TokenUtils.isValid(token)) {
            Map<String, Object> tokenBody = TokenUtils.getBody(token);
            return (Integer) tokenBody.get(AccountConstants.MEMBER_ID_NAME);
        }
        return null;
    }

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
        Integer memberId = member.getId();

        String accessToken = TokenUtils.generate(AccountConstants.ACCESS_TOKEN_NAME, AccountConstants.MEMBER_ID_NAME, memberId, AccountConstants.ACCESS_TOKEN_EXP_MINUTES);

        String refreshToken = TokenUtils.generate(AccountConstants.REFRESH_TOKEN_NAME, AccountConstants.MEMBER_ID_NAME, memberId, AccountConstants.REFRESH_TOKEN_EXP_MINUTES);

        HttpUtils.setCookie(res, AccountConstants.REFRESH_TOKEN_NAME, refreshToken, AccountConstants.REFRESH_TOKEN_EXP_MINUTES);

        return accessToken;
    }

    @Override
    public Integer getMemberId(HttpServletRequest req) {
        return this.getMemberId(getAccessToken(req));
    }

    @Override
    public boolean isLoggedIn(HttpServletRequest req) {
        if(TokenUtils.isValid(getAccessToken(req))) {
            return true;
        }

        String refreshToken = getRefreshToken(req);
        return TokenUtils.isValid(refreshToken) && !blockService.has(refreshToken);
    }

    @Override
    public void logout(HttpServletRequest req, HttpServletResponse res) {
        String refreshToken = getRefreshToken(req);

        if(refreshToken != null) {
            HttpUtils.removeCookie(res, AccountConstants.REFRESH_TOKEN_NAME);
        }

        // 토큰 차단 데이터에 해당 토큰이 없으면
        if(!blockService.has(refreshToken)) {
            blockService.add(refreshToken);
        }
    }
}
