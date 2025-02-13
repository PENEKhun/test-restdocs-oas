package org.penekhun.restdocs.global.security.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.penekhun.restdocs.global.security.auth.CustomUserDetailsService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements InitializingBean {

  private static final String TOKEN = "token";
  private static final String USERNAME = "username";
  private final String SECRET_KEY = "TESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTEST";
  private final long TOKEN_TIMEOUT_MS = 86_400 * 1_000;
  private final CustomUserDetailsService userDetailsService;
  private Key key;

  @Override
  public void afterPropertiesSet() {
    // 빈 생성시 키 값 전달
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  public Map<String, String> createToken(String username) {

    long now = (new Date()).getTime();
    Date validity = new Date(now + this.TOKEN_TIMEOUT_MS);

    Map<String, String> map = new HashMap<>();
    map.put(TOKEN, Jwts.builder()
        .claim(USERNAME, username)
        .signWith(key, SignatureAlgorithm.HS512)
        .setExpiration(validity)
        .compact());
    map.put("tokenExpired", String.valueOf(validity));

    return map;
  }

  public Authentication getAuthentication(String token) {
    Claims claims = Jwts
        .parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
    String username = claims.get(USERNAME, String.class);

    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public boolean validateToken(String accessToken) {
    Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
    return true;
  }

}

