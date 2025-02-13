package org.penekhun.restdocs.recruitment.repository;

import org.penekhun.restdocs.recruitment.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
  
}
