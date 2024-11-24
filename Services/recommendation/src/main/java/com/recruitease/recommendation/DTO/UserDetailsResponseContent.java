package com.recruitease.recommendation.DTO;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDetailsResponseContent{

        //reqested ids
        private List<String> recruiterIdList;
        private List<String> candidateIdList;
        private List<String> moderatorIdList;
        private List<String> adminIdList;

        //response lists
        private List<Object> recruiterList;
        private List<CandidateData> candidateList;
        private List<Object> moderatorList;
        private List<Object> adminList;

}
