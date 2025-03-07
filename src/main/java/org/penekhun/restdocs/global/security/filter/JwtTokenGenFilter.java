package org.penekhun.restdocs.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.penekhun.restdocs.global.security.auth.CustomUser;
import org.penekhun.restdocs.global.security.provider.JwtTokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * ID, PW 를 이용해서 토큰을 생성하는 필터
 */
@Slf4j
public class JwtTokenGenFilter extends UsernamePasswordAuthenticationFilter {

  public static final String AUTHORIZATION_HEADER = "Authorization";
  protected static final String LOGIN_URI = "/api/v1/login";
  private static final String TOKEN_PREFIX = "Bearer ";

  private final JwtTokenProvider tokenProvider;

  public JwtTokenGenFilter(JwtTokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;

    setFilterProcessesUrl(LOGIN_URI);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response)
      throws AuthenticationException {
    UserLogin userLogin = readUserLoginFromRequest(request);
    return getAuthenticationManager().authenticate(
        new UsernamePasswordAuthenticationToken(
            userLogin.username,
            userLogin.password,
            null
        ));
  }

  private UserLogin readUserLoginFromRequest(HttpServletRequest request) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readValue(request.getInputStream(), UserLogin.class);
    } catch (IOException e) {
      log.error("Error reading UserLogin from request: {}", e.getMessage());
      throw new RuntimeException("Error reading UserLogin", e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain,
      Authentication authResult) {

    CustomUser principalDetails = (CustomUser) authResult.getPrincipal();

    String jwtToken = tokenProvider.createToken(principalDetails.getUsername())
        .get("token");

    response.addHeader(AUTHORIZATION_HEADER, TOKEN_PREFIX + jwtToken);
  }

  private record UserLogin(
      String username,
      String password
  ) {

  }
}
