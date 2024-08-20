package com.recruitease.auth_service.service;

import com.recruitease.auth_service.DTO.*;
import com.recruitease.auth_service.DTO.roleDetails.AdminRoleDetail;
import com.recruitease.auth_service.DTO.roleDetails.CandidateRoleDetail;
import com.recruitease.auth_service.DTO.roleDetails.ModeratorRoleDetail;
import com.recruitease.auth_service.DTO.roleDetails.RecruiterRoleDetail;
import com.recruitease.auth_service.config.CustomUserDetails;
import com.recruitease.auth_service.entity.*;
import com.recruitease.auth_service.repository.*;
import com.recruitease.auth_service.util.CodeList;
import com.recruitease.auth_service.util.RoleList;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponseException;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserCredentialRepository repository;
    private final CandidateRepository candidateRepository;
    private final RecruiterRepository recruiterRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AdminRepository adminRepository;
    private final ModeratorRepository moderatorRepository;

    public String saveUser(AuthRequest request) {

        //mapping
        UserCredential userCredential= modelMapper.map(request,UserCredential.class);
        //encrypting password
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        //saving to db
        var user=repository.save(userCredential);
        return user.getId();
    }

    public String generateToken(String userId,Map<String, Object> claims){
        return jwtService.generateToken(userId,claims);
    }

    public Boolean validateToken(String token){
        System.out.println(token);
        return jwtService.validateToken(token);
    }

    @Transactional //to make it save both as one transaction
    public ResponseDTO registerCandidate(CandidateRequest request) {
        var responseDTO=new ResponseDTO();

        var errors=new HashMap<String,String >();


        //mapping
        UserCredential userCredential= modelMapper.map(request,UserCredential.class);
        Candidate candidate= modelMapper.map(request,Candidate.class);
        //encrypting password
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        //set role
        userCredential.setRole(RoleList.ROLE_CANDIDATE);


        //validations
        if(repository.existsByEmail(userCredential.getEmail())){
            errors.put("email","Email address already exists!");
        }
        if(candidateRepository.existsByMobileNumber(candidate.getMobileNumber())){
            errors.put("mobileNumber","Mobile Number already exists!");
        }
        if(candidateRepository.existsByNic(candidate.getNic())){
            errors.put("nic","NIC Number already exists!");
        }

        if(errors.isEmpty()){
            //saving to db
            var user=repository.save(userCredential);
            candidate.setUser(user);
            var candidateObj=candidateRepository.save(candidate);

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Candidate registered successfully");
            responseDTO.setContent(user.getId());

        }else {
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);

        }
        return responseDTO;


    }

    @Transactional //to make it save both as one transaction
    public ResponseDTO registerRecruiter(RecruiterRequest request) {
        var responseDTO=new ResponseDTO();

        var errors=new HashMap<String,String >();


        //mapping
        UserCredential userCredential= modelMapper.map(request,UserCredential.class);
        Recruiter recruiter= modelMapper.map(request,Recruiter.class);
        //encrypting password
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        //set role
        userCredential.setRole(RoleList.ROLE_RECRUITER);


        //validations
        if(repository.existsByEmail(userCredential.getEmail())){
            errors.put("email","Email address already exists!");
        }
        if(recruiterRepository.existsByMobileNumber(recruiter.getMobileNumber())){
            errors.put("mobileNumber","Mobile Number already exists!");
        }
        if(recruiterRepository.existsByBusinessRegistrationNumber(recruiter.getBusinessRegistrationNumber())){
            errors.put("businessRegistrationNumber","BR Number already exists!");
        }

        if(errors.isEmpty()){
            //saving to db
            var user=repository.save(userCredential);
            recruiter.setUser(user);
            var recruiterObj=recruiterRepository.save(recruiter);

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Recruiter registered successfully");
            responseDTO.setContent(user.getId());

        }else {
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);

        }
        return responseDTO;


    }



    @Transactional
    public ResponseDTO registerAdmin(AdminModeratorRequest request) {
        var responseDTO=new ResponseDTO();

        var errors=new HashMap<String,String >();
        //mapping
        UserCredential userCredential= modelMapper.map(request,UserCredential.class);
        Admin admin= modelMapper.map(request,Admin.class);
        //encrypting password
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        //set role
        userCredential.setRole(RoleList.ROLE_ADMIN);

//validations
        if(repository.existsByEmail(userCredential.getEmail())){
            errors.put("email","Email address already exists!");
        }
        if(adminRepository.existsByMobileNumber(admin.getMobileNumber())){
            errors.put("mobileNumber","Mobile Number already exists!");
        }

        if(errors.isEmpty()){
            //saving to db
            var user=repository.save(userCredential);
            admin.setUser(user);
            var adminObj=adminRepository.save(admin);

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Admin registered successfully");
            responseDTO.setContent(user.getId());

        }else {
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);

        }

        return responseDTO;

    }



    @Transactional
    public ResponseDTO registerModerator(AdminModeratorRequest request) {
        var responseDTO=new ResponseDTO();

        var errors=new HashMap<String,String >();

        //mapping
        UserCredential userCredential= modelMapper.map(request,UserCredential.class);
        Moderator moderator= modelMapper.map(request, Moderator.class);
        //encrypting password
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        //set role
        userCredential.setRole(RoleList.ROLE_MODERATOR);


//validations
        if(repository.existsByEmail(userCredential.getEmail())){
            errors.put("email","Email address already exists!");
        }
        if(moderatorRepository.existsByMobileNumber(moderator.getMobileNumber())){
            errors.put("mobileNumber","Mobile Number already exists!");
        }

        if(errors.isEmpty()){
            //saving to db
            var user=repository.save(userCredential);
            moderator.setUser(user);
            var moderatorObj=moderatorRepository.save(moderator);

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Moderator registered successfully");
            responseDTO.setContent(user.getId());

        }else {
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);

        }
        return responseDTO;
    }


    public SessionObjectResponse generateSessionObj(String userId) throws AuthenticationException {
            //not checking whether its present since we already authenticated
        try{
            UserCredential user=repository.findById(userId).get();

            SessionObjectResponse res=modelMapper.map(user,SessionObjectResponse.class);



            //roleDetails object to add to jwt without sensitive data
            Map<String, Object> roleDetails=new HashMap<>();



            //get  obj relevant to role
            if(user.getRole().equals(RoleList.ROLE_CANDIDATE)){
                res.setRole("candidate");
                Candidate candidate=candidateRepository.findByUserId(userId).get();
                CandidateRoleDetail roleDetail=modelMapper.map(candidate,CandidateRoleDetail.class);
                res.setRoleDetails(roleDetail);

                //details for jwt claims
                roleDetails.put("firstName",candidate.getFirstName());
                roleDetails.put("lastName",candidate.getLastName());
                roleDetails.put("profilePic",candidate.getProfilePic());
                roleDetails.put("gender",candidate.getGender());
                roleDetails.put("city",candidate.getCity());
                roleDetails.put("profileStatus",candidate.getProfileStatus());
                roleDetails.put("candidateId",candidate.getCandidateId());

            } else if (user.getRole().equals(RoleList.ROLE_RECRUITER)) {
                res.setRole("recruiter");
                Recruiter recruiter=recruiterRepository.findByUserId(userId).get();
                RecruiterRoleDetail roleDetail=modelMapper.map(recruiter,RecruiterRoleDetail.class);
                res.setRoleDetails(roleDetail);

                //details for jwt claims
                roleDetails.put("firstName",recruiter.getFirstName());
                roleDetails.put("lastName",recruiter.getLastName());
                roleDetails.put("profilePic",recruiter.getProfilePic());
                roleDetails.put("gender",recruiter.getGender());
                roleDetails.put("city",recruiter.getCity());
                roleDetails.put("recruiterId",recruiter.getRecruiterId());
                roleDetails.put("companyName",recruiter.getCompanyName());
                roleDetails.put("businessRegistrationNumber",recruiter.getBusinessRegistrationNumber());
                roleDetails.put("website",recruiter.getWebsite());

            } else if (user.getRole().equals(RoleList.ROLE_ADMIN)) {
                res.setRole("admin");
                Admin admin=adminRepository.findByUserId(userId).get();
                AdminRoleDetail roleDetail=modelMapper.map(admin,AdminRoleDetail.class);
                res.setRoleDetails(roleDetail);


                //details for jwt claims
                roleDetails.put("firstName",admin.getFirstName());
                roleDetails.put("lastName",admin.getLastName());
                roleDetails.put("profilePic",admin.getProfilePic());
                roleDetails.put("gender",admin.getGender());
                roleDetails.put("city",admin.getCity());
                roleDetails.put("adminId",admin.getAdminId());

            }else{
                res.setRole("moderator");
                Moderator moderator=moderatorRepository.findByUserId(userId).get();
                ModeratorRoleDetail roleDetail=modelMapper.map(moderator,ModeratorRoleDetail.class);
                res.setRoleDetails(roleDetail);

                //details for jwt claims
                roleDetails.put("firstName",moderator.getFirstName());
                roleDetails.put("lastName",moderator.getLastName());
                roleDetails.put("profilePic",moderator.getProfilePic());
                roleDetails.put("gender",moderator.getGender());
                roleDetails.put("city",moderator.getCity());
                roleDetails.put("moderatorId",moderator.getModeratorId());
            }

            //claims to add to jwt without sensitive data
            Map<String, Object> claims=new HashMap<>();
            claims.put("id",userId);
            claims.put("email",res.getEmail());
            claims.put("role",res.getRole());
            claims.put("createdAt",res.getCreatedAt());
            claims.put("isActive",res.getIsActive());
            claims.put("roleDetails",roleDetails);


            res.setAccessToken(this.generateToken(userId,claims));
            return res;
        }catch (Exception e){
            throw new AuthenticationException("Error while authenticating!");
        }


    }
}
