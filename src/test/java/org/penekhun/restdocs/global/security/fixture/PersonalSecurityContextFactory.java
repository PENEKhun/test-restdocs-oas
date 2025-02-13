package org.penekhun.restdocs.global.security.fixture;

import org.penekhun.restdocs.global.security.auth.CustomUser;
import org.penekhun.restdocs.user.entity.PersonalUserAccount;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class PersonalSecurityContextFactory implements
    WithSecurityContextFactory<TestWithPersonalAccount> {

  @Override
  public SecurityContext createSecurityContext(TestWithPersonalAccount annotation) {
    final SecurityContext context = SecurityContextHolder.createEmptyContext();

    PersonalUserAccount account = PersonalUserAccount.builder().username(annotation.username())
        .password(annotation.password()).build();
    CustomUser userDetails = new CustomUser(account, true);
    final Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null,
        userDetails.getAuthorities());
    context.setAuthentication(auth);
    return context;
  }
}
