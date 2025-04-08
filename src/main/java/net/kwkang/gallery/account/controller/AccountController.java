package net.kwkang.gallery.account.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.kwkang.gallery.account.dto.AccountJoinRequest;
import net.kwkang.gallery.account.dto.AccountLoginRequest;
import net.kwkang.gallery.account.etc.AccountConstants;
import net.kwkang.gallery.account.helper.AccountHelper;
import net.kwkang.gallery.block.service.BlockService;
import net.kwkang.gallery.common.utill.HttpUtils;
import net.kwkang.gallery.common.utill.TokenUtils;
import net.kwkang.gallery.member.service.MemberService;
import org.antlr.v4.runtime.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AccountController {
    private final AccountHelper accountHelper;
    private final BlockService blockService;
    private final MemberService memberService;

    @PostMapping("/api/account/join")
    public ResponseEntity<?> join(@RequestBody AccountJoinRequest joinReq) {
        // 입력 값이 비어있다면
        if(!StringUtils.hasLength(joinReq.getName())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(memberService.find(joinReq.getLoginId()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }


        accountHelper.join(joinReq);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/account/login")
    public ResponseEntity<?> login(HttpServletRequest req, HttpServletResponse res, @RequestBody AccountLoginRequest loginReq) {
        if(!StringUtils.hasLength(loginReq.getLoginId()) || !StringUtils.hasLength(loginReq.getLoginPw())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String loginId = accountHelper.login(loginReq, req, res);

        if(loginId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(loginId, HttpStatus.OK);
    }

    @GetMapping("/api/account/check")
    public ResponseEntity<?> check(HttpServletRequest req) {
        return new ResponseEntity<>(accountHelper.isLoggedIn(req), HttpStatus.OK);
    }

    @PostMapping("/api/account/logout")
    public ResponseEntity<?> logout(HttpServletRequest req, HttpServletResponse res) {
        accountHelper.logout(req, res);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/account/token")
    public ResponseEntity<?> regenerate(HttpServletRequest req) {
        String accessToken = "";
        String refreshToken = HttpUtils.getCookie(req, AccountConstants.REFRESH_TOKEN_NAME);

        if(StringUtils.hasLength(refreshToken) && TokenUtils.isValid(refreshToken) && !blockService.has(refreshToken)) {
            Map<String, Object> tokenBody = TokenUtils.getBody(refreshToken);
            Integer memberId = (Integer) tokenBody.get(AccountConstants.MEMBER_ID_NAME);

            accessToken = TokenUtils.generate(AccountConstants.ACCESS_TOKEN_NAME, AccountConstants.MEMBER_ID_NAME, memberId, AccountConstants.ACCESS_TOKEN_EXP_MINUTES);
        }
        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }
}
