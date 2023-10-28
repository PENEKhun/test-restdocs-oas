package org.penekhun.wanted2023.global.security.auth;

import java.util.Collection;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.penekhun.wanted2023.user.entity.EnterpriseUserAccount;
import org.penekhun.wanted2023.user.entity.PersonalUserAccount;
import org.penekhun.wanted2023.user.entity.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Slf4j
@Getter
public class CustomUser extends User {

  private final boolean isPersonalUser;
  private PersonalUserAccount personalUser;
  private EnterpriseUserAccount enterpriseUser;

  public CustomUser(UserAccount account, boolean isPersonalUser,
      Collection<? extends GrantedAuthority> authorities) {
    super(account.getUsername(), account.getPassword(), authorities);
    this.isPersonalUser = isPersonalUser;

    if (isPersonalUser) {
      this.personalUser = (PersonalUserAccount) account;
    } else {
      this.enterpriseUser = (EnterpriseUserAccount) account;
    }
  }

  public UserAccount getAccount() {
    if (isPersonalUser) {
      return personalUser;
    } else {
      return enterpriseUser;
    }
  }

}
