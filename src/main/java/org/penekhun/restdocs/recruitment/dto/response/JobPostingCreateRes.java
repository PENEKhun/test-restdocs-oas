package org.penekhun.restdocs.recruitment.dto.response;

import lombok.Builder;

@Builder
public record JobPostingCreateRes(
    Long id,
    int recruitReward,
    String recruitPosition,
    String description,
    String requiredSkill
) {

}
