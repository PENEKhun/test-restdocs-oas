package org.penekhun.restdocs.recruitment.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.penekhun.restdocs.global.docs.RestDocsSupport;
import org.penekhun.restdocs.recruitment.dto.request.JobPostingCreateReq;
import org.penekhun.restdocs.recruitment.dto.response.JobPostingCreateRes;
import org.penekhun.restdocs.recruitment.dto.response.JobPostingSearchRes;
import org.penekhun.restdocs.recruitment.service.JobPostingService;
import org.penekhun.restdocs.user.dto.EnterpriseUserRes;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

@WebMvcTest(JobPostingController.class)
@DisplayName("채용공고 컨트롤러 테스트")
class JobPostingControllerTest extends RestDocsSupport {

  @MockBean
  JobPostingService jobPostingService;

  @Test
  @DisplayName("채용 공고 생성에 성공한다.")
  void create_job_posting() throws Exception {

    var input = new JobPostingCreateReq(
        1000000,
        "개발자",
        "개발자를 채용합니다.",
        "python"
    );

    given(jobPostingService.createJobPosting(any(), any()))
        .willReturn(JobPostingCreateRes.builder()
            .id(1L)
            .recruitPosition(input.recruitPosition())
            .recruitReward(input.recruitReward())
            .description(input.description())
            .requiredSkill(input.requiredSkill())
            .build());

    mockMvc
        .perform(
                post("/api/v1/job-posting")
                .header("Authorization", "Bearer {{ACCESS_TOKEN}}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
        .andExpect(status().isOk())
        .andDo(document("job-posting-create",
            "채용 공고 생성",
            requestFields(
                fieldWithPath("recruitPosition")
                    .type(JsonFieldType.STRING)
                    .description("채용 포지션"),
                fieldWithPath("recruitReward")
                    .type(JsonFieldType.NUMBER)
                    .description("채용 보상"),
                fieldWithPath("description")
                    .type(JsonFieldType.STRING)
                    .description("채용 공고 설명"),
                fieldWithPath("requiredSkill")
                    .type(JsonFieldType.STRING)
                    .description("사용 기술")
            ),
            responseFields(responseCommon())
                .andWithPrefix("data.",
                    fieldWithPath("id")
                        .type(JsonFieldType.NUMBER)
                        .description("채용 공고 ID"),
                    fieldWithPath("recruitPosition")
                        .type(JsonFieldType.STRING)
                        .description("채용 포지션"),
                    fieldWithPath("recruitReward")
                        .type(JsonFieldType.NUMBER)
                        .description("채용 보상"),
                    fieldWithPath("description")
                        .type(JsonFieldType.STRING)
                        .description("채용 공고 설명"),
                    fieldWithPath("requiredSkill")
                        .type(JsonFieldType.STRING)
                        .description("사용 기술")
                )
        ))
        .andReturn();
  }

  @Test
  @DisplayName("채용 공고 수정에 성공한다.")
  void update_job_posting() throws Exception {

    var input = new JobPostingCreateReq(
        1000000,
        "개발자",
        "개발자를 채용합니다.",
        "python"
    );

    given(jobPostingService.updateMyJobPosting(any(), any(), any()))
        .willReturn(JobPostingCreateRes.builder()
            .id(1L)
            .recruitPosition(input.recruitPosition())
            .recruitReward(input.recruitReward())
            .description(input.description())
            .requiredSkill(input.requiredSkill())
            .build());

    mockMvc
        .perform(
                patch("/api/v1/job-posting/{jobPostId}", 1L)
                .header("Authorization", "Bearer {{ACCESS_TOKEN}}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
        .andExpect(status().isOk())
        .andDo(document(
                "job-posting-update",
            "채용 공고 수정",
            pathParameters(
                parameterWithName("jobPostId")
                    .description("수정하려는 채용 공고 id"))
            ,
            requestFields(
                fieldWithPath("recruitPosition")
                    .type(JsonFieldType.STRING)
                    .description("채용 포지션")
                    .optional(),
                fieldWithPath("recruitReward")
                    .type(JsonFieldType.NUMBER)
                    .description("채용 보상")
                    .optional(),
                fieldWithPath("description")
                    .type(JsonFieldType.STRING)
                    .description("채용 공고 설명")
                    .optional(),
                fieldWithPath("requiredSkill")
                    .type(JsonFieldType.STRING)
                    .description("사용 기술")
                    .optional()
            ),
            responseFields(responseCommon())
                .andWithPrefix("data.",
                    fieldWithPath("id")
                        .type(JsonFieldType.NUMBER)
                        .description("채용 공고 ID"),
                    fieldWithPath("recruitPosition")
                        .type(JsonFieldType.STRING)
                        .description("채용 포지션"),
                    fieldWithPath("recruitReward")
                        .type(JsonFieldType.NUMBER)
                        .description("채용 보상"),
                    fieldWithPath("description")
                        .type(JsonFieldType.STRING)
                        .description("채용 공고 설명"),
                    fieldWithPath("requiredSkill")
                        .type(JsonFieldType.STRING)
                        .description("사용 기술")
                )
        ))
        .andReturn();
  }

  @Test
  @DisplayName("채용 공고 조회에 성공한다.")
  void search_job_posting_withPage() throws Exception {
    var item = JobPostingSearchRes.builder()
        .id(1L)
        .recruitPosition("개발자")
        .recruitReward(1000000)
        .description("개발자를 채용합니다.")
        .requiredSkill("python")
        .enterprise(
            EnterpriseUserRes.builder()
                .id(1L)
                .name("naver")
                .nationCode("KR")
                .provinceCode("경기도")
                .build()
        )
        .build();

    when(jobPostingService.getJobPostings(any()))
        .thenReturn(new PageImpl<>(Collections.singletonList(item)));

    mockMvc
        .perform(
            get("/api/v1/job-posting")
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document("job-posting-search",
                "채용 공고 조회",
                pathParameters(
                    requestPageable()
                ),
                responseFields(
                    responseCommon())
                    .and(responsePage())
                    .andWithPrefix("data.content[].",
                        fieldWithPath("id")
                            .type(JsonFieldType.NUMBER)
                            .description("채용 공고 ID"),
                        fieldWithPath("recruitPosition")
                            .type(JsonFieldType.STRING)
                            .description("채용 포지션"),
                        fieldWithPath("recruitReward")
                            .type(JsonFieldType.NUMBER)
                            .description("채용 보상"),
                        fieldWithPath("description")
                            .type(JsonFieldType.STRING)
                            .description("채용 공고 설명"),
                        fieldWithPath("requiredSkill")
                            .type(JsonFieldType.STRING)
                            .description("사용 기술"),
                        fieldWithPath("enterprise")
                            .type(JsonFieldType.OBJECT)
                            .description("채용 기업 정보")
                    ).andWithPrefix("data.content[].enterprise.",
                        fieldWithPath("id")
                            .type(JsonFieldType.NUMBER)
                            .description("기업 ID"),
                        fieldWithPath("name")
                            .type(JsonFieldType.STRING)
                            .description("기업 이름"),
                        fieldWithPath("nationCode")
                            .type(JsonFieldType.STRING)
                            .description("기업 국가 코드"),
                        fieldWithPath("provinceCode")
                            .type(JsonFieldType.STRING)
                            .description("기업 지역 코드")
                    )
            ));
  }


  @Test
  @DisplayName("채용 공고 삭제에 성공한다")
  void delete_job_posting() throws Exception {
    mockMvc
        .perform(
            RestDocumentationRequestBuilders
                .delete("/api/v1/job-posting/{jobPostId}", 1L)
                .header("Authorization", "Bearer {{ACCESS_TOKEN}}")
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(
            restDocs.document(
                pathParameters(
                    parameterWithName("jobPostId")
                        .description("삭제하려는 채용 공고 id"))
            ));
  }
}
