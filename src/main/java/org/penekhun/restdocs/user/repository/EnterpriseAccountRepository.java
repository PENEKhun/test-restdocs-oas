package org.penekhun.restdocs.user.repository;

import java.util.Optional;
import org.penekhun.restdocs.user.entity.EnterpriseUserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterpriseAccountRepository extends JpaRepository<EnterpriseUserAccount, Long> {

  Optional<EnterpriseUserAccount> findByUsername(String username);

}
