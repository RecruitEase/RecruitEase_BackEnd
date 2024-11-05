package com.recruitease.recommendation.DTO;

import lombok.*;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CandidateRequest {

    private List<String> keywords;
}
