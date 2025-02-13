package org.penekhun.restdocs.global.security.auth;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.penekhun.restdocs.user.entity.EnterpriseUserAccount;
import org.penekhun.restdocs.user.entity.PersonalUserAccount;
import org.penekhun.restdocs.user.entity.UserAccount;
import org.penekhun.restdocs.user.repository.EnterpriseAccountRepository;
import org.penekhun.restdocs.user.repository.PersonalAccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final PersonalAccountRepository personalAccountRepository;
  private final EnterpriseAccountRepository enterpriseAccountRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("called loadUserByUsername. username: {}", username);

    UserAccount userAccount = findUserByUsername(username);

    return new CustomUser(userAccount, userAccount instanceof PersonalUserAccount);
  }

  private UserAccount findUserByUsername(String username) {
    Optional<PersonalUserAccount> foundPersonalUser = personalAccountRepository.findByUsername(
        username);
    Optional<EnterpriseUserAccount> foundEnterpriseUser = enterpriseAccountRepository.findByUsername(
        username);

    if (foundPersonalUser.isPresent()) {
      return foundPersonalUser.get();
    } else if (foundEnterpriseUser.isPresent()) {
      return foundEnterpriseUser.get();
    } else {
      throw new EntityNotFoundException("User not found");
    }
  }
}
