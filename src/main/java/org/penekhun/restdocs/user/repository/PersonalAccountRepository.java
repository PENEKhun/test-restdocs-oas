package org.penekhun.restdocs.user.repository;

import java.util.Optional;
import org.penekhun.restdocs.user.entity.PersonalUserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalAccountRepository extends JpaRepository<PersonalUserAccount, Long> {

  Optional<PersonalUserAccount> findByUsername(String username);

}
