package com.recruitease.auth_service.DTO;

import com.recruitease.auth_service.DTO.LoggedUser.LoggedAdmin;
import com.recruitease.auth_service.DTO.LoggedUser.LoggedCandidate;
import com.recruitease.auth_service.DTO.LoggedUser.LoggedModerator;
import com.recruitease.auth_service.DTO.LoggedUser.LoggedRecruiter;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDetailsResponseDTO{

        //reqested ids
        private List<String> recruiterIdList;
        private List<String> candidateIdList;
        private List<String> moderatorIdList;
        private List<String> adminIdList;

        //response lists
        private List<LoggedRecruiter> recruiterList;
        private List<LoggedCandidate> candidateList;
        private List<LoggedModerator> moderatorList;
        private List<LoggedAdmin> adminList;

}
