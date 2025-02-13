package org.penekhun.restdocs.recruitment.dto.response;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.penekhun.restdocs.recruitment.fixture.RecruitmentFixture.CreateJobPosting;
import static org.penekhun.restdocs.user.UserFixture.CreateEnterpriseUserAccount;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.penekhun.restdocs.user.dto.EnterpriseUserRes;
import org.springframework.test.util.ReflectionTestUtils;

@Disabled
class JobPostingSearchResTest {

  @RepeatedTest(5)
  @DisplayName("Entity(JobPosting) to Dto(JobPostingSearchRes) 가 정상 동작합니다.")
  void From_should_return_JobPostingSearchRes() {
    // given
    var company = CreateEnterpriseUserAccount();
    var jobPosting = CreateJobPosting();
    ReflectionTestUtils.setField(company, "id", 1L);
    ReflectionTestUtils.setField(jobPosting, "company", company);
    ReflectionTestUtils.setField(jobPosting, "id", 1L);

    // when
    JobPostingSearchRes result = JobPostingSearchRes.from(jobPosting);

    // then
    assertThat(result)
        .extracting(
            "id",
            "enterprise",
            "recruitReward",
            "recruitPosition",
            "description",
            "requiredSkill")
        .containsExactly(jobPosting.getId(),
            EnterpriseUserRes.from(jobPosting.getCompany()),
            jobPosting.getRecruitReward(),
            jobPosting.getRecruitPosition(),
            jobPosting.getDescription(),
            jobPosting.getRequiredSkill()
        );
  }
}
