package org.penekhun.restdocs.recruitment.dto.response;

import lombok.Builder;
import org.penekhun.restdocs.recruitment.entity.JobPosting;
import org.penekhun.restdocs.user.dto.EnterpriseUserRes;

@Builder
public record JobPostingSearchRes(
    Long id,
    EnterpriseUserRes enterprise,
    int recruitReward,
    String recruitPosition,
    String description,
    String requiredSkill
) {

  public static JobPostingSearchRes from(JobPosting jobPosting) {
    return JobPostingSearchRes.builder()
        .id(jobPosting.getId())
        .enterprise(EnterpriseUserRes.from(jobPosting.getCompany()))
        .recruitReward(jobPosting.getRecruitReward())
        .recruitPosition(jobPosting.getRecruitPosition())
        .description(jobPosting.getDescription())
        .requiredSkill(jobPosting.getRequiredSkill())
        .build();
  }
}
