package com.recruitease.auth_service.DTO;

import com.recruitease.auth_service.DTO.LoggedUser.LoggedAdmin;
import com.recruitease.auth_service.DTO.LoggedUser.LoggedCandidate;
import com.recruitease.auth_service.DTO.LoggedUser.LoggedModerator;
import com.recruitease.auth_service.DTO.LoggedUser.LoggedRecruiter;
import com.recruitease.auth_service.DTO.roleDetails.AdminRoleDetail;
import com.recruitease.auth_service.DTO.roleDetails.CandidateRoleDetail;
import com.recruitease.auth_service.DTO.roleDetails.ModeratorRoleDetail;
import com.recruitease.auth_service.DTO.roleDetails.RecruiterRoleDetail;

import java.util.List;

public record UserDetailsResponseDTO(

        //reqested ids
        List<String> recruiterIdList,
        List<String> candidateIdList,
        List<String> moderatorIdList,
        List<String> adminIdList,

        //response lists
        List<LoggedRecruiter> recruiterList,
        List<LoggedCandidate> candidateList,
        List<LoggedModerator> moderatorList,
        List<LoggedAdmin> adminList
) {
}
