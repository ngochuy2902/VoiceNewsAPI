package com.news.voicenews.api.auth;

import com.news.voicenews.bloc.JwtBloc;
import com.news.voicenews.bloc.RegisterBloc;
import com.news.voicenews.dto.req.LoginReq;
import com.news.voicenews.dto.req.RefreshTokenReq;
import com.news.voicenews.dto.req.RegisterReq;
import com.news.voicenews.dto.res.TokenRes;
import com.news.voicenews.security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtBloc jwtBloc;
    private final AuthenticationManager authenticationManager;
    private final RegisterBloc registerBloc;

    public AuthController(final JwtBloc jwtBloc, final AuthenticationManager authenticationManager, final RegisterBloc registerBloc) {
        this.jwtBloc = jwtBloc;
        this.authenticationManager = authenticationManager;

        this.registerBloc = registerBloc;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterReq registerReq) {
        registerBloc.register(registerReq);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenRes> login(@RequestBody final LoginReq loginReq) {
        Authentication authentication =
                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUsername(),
                                                                              loginReq.getPassword()));
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        TokenRes tokenRes = jwtBloc.generateToken(userDetails);

        return ResponseEntity.ok(tokenRes);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenRes> refreshToken(@RequestBody final RefreshTokenReq refreshTokenReq) {

        // TODO: Get refresh token from Redis to make sure that it is exists for current user
        if (!jwtBloc.isExpiredToken(refreshTokenReq)) {
            return ResponseEntity.ok(jwtBloc.generateToken(refreshTokenReq));
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


}
