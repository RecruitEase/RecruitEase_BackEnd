package com.cv_service.cv_service.DTO;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CvSetResponseDTO {

        //reqested ids
        private List<String> candidateIds;

}
