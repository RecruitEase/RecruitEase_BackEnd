package com.recruitease.application_service.service;

import com.recruitease.application_service.DTO.OfferRequest;
import com.recruitease.application_service.DTO.OfferUpdateRequest;
import com.recruitease.application_service.DTO.ResponseDTO;
import com.recruitease.application_service.config.CustomUserDetails;
import com.recruitease.application_service.entity.Application;
import com.recruitease.application_service.entity.Offer;
import com.recruitease.application_service.repository.ApplicationRepository;
import com.recruitease.application_service.repository.OfferRepository;
import com.recruitease.application_service.util.CodeList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository repository;
    private final ApplicationRepository applicationRepository;
    private final ModelMapper modelMapper;


    // Sri Lanka time zone
    private static final ZoneId SRI_LANKA_ZONE = ZoneId.of("Asia/Colombo");


    // Run this task every day at midnight Sri Lanka time
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Colombo")
    public void updateExpiredJobOffers() {
        // Get the current time in Sri Lankan time zone
        ZonedDateTime zonedNow = ZonedDateTime.now(SRI_LANKA_ZONE);
        LocalDateTime nowSriLankaTime = zonedNow.toLocalDateTime();

        // Log the execution time for debugging
        System.out.println("Executing offer update task at: " + zonedNow);

        // Fetch all pending job offers
        List<Offer> activeJobOffers = repository.findByStatus(Offer.OfferStatus.PENDING);
        int expiredCount = 0;

        for (Offer jobOffer : activeJobOffers) {
            // Check if the final acceptance date has passed according to Sri Lankan time
            if (jobOffer.getFinalAcceptanceDateTime() != null &&
                    jobOffer.getFinalAcceptanceDateTime().isBefore(nowSriLankaTime)) {
                // Set status to expired
                jobOffer.setStatus(Offer.OfferStatus.EXPIRED);
                repository.save(jobOffer);
                expiredCount++;

                System.out.println("Expired offer with ID: " + jobOffer.getOfferId() +
                        ", Final acceptance datetime: " +
                        jobOffer.getFinalAcceptanceDateTime());
            }
        }

        System.out.println("Total offers expired: " + expiredCount);
    }


    public ResponseDTO createOffer(@Valid OfferRequest request) {
        var responseDTO=new ResponseDTO();
        var errors=new HashMap<String,String >();


        Offer offer=modelMapper.map(request,Offer.class);
        // Fetch the application by ID
        Optional<Application> applicationOptional = applicationRepository.findById(request.applicationId());

        if (applicationOptional.isPresent()) {
            Application application = applicationOptional.get();
            offer.setCreatedAt(LocalDateTime.now(SRI_LANKA_ZONE));

            offer.setStatus(Offer.OfferStatus.PENDING);
            offer.setApplication(application);


        } else {
            errors.put("applicationId","Invalid applicationId!");
        }


        if(errors.isEmpty()){
            //saving to db
            var res=repository.save(offer);

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Offer created successfully");
            responseDTO.setContent(res.getOfferId());

        }else {
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }
        return responseDTO;
    }

    public ResponseDTO getOffer(String offerId) {
        var responseDTO=new ResponseDTO();
        var errors=new HashMap<String,String >();


        if(repository.existsById(offerId)){
            Offer res=repository.findById(offerId).orElse(null);


            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(res);
        }else{
            errors.put("offer","Not found!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }


        return responseDTO;
    }

    public ResponseDTO getOffersPerCandidate(String candidateId) {

        var responseDTO=new ResponseDTO();
        try {
            //get list of applications for the given candidateId
            var res = repository.findByCandidateId(candidateId)
                    .stream()
                    .map(source->modelMapper.map(source,Offer.class))
                    .toList();

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(res);
        }catch (Exception e){
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(null);
        }


        return responseDTO;
    }

    public ResponseDTO getOffersPerRecruiter(String recruiterId) {

        var responseDTO=new ResponseDTO();
        try {
            //get list of applications for the given candidateId
            var res = repository.findByRecruiterId(recruiterId)
                    .stream()
                    .map(source->modelMapper.map(source,Offer.class))
                    .toList();

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(res);
        }catch (Exception e){
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(null);
        }


        return responseDTO;
    }

    public ResponseDTO getOffersPerJob(String jobId) {
        var responseDTO=new ResponseDTO();
        try {
            //get list of applications for the given candidateId
            var res = repository.findByJobId(jobId)
                    .stream()
                    .map(source->modelMapper.map(source,Offer.class))
                    .toList();

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(res);
        }catch (Exception e){
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(null);
        }


        return responseDTO;
    }

    @Transactional
    public ResponseDTO updateOffer(String offerId,OfferUpdateRequest updateReq) {
        var responseDTO = new ResponseDTO();
        var errors = new HashMap<String, String>();

        try {

            //get canidate id of logged user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Optional<Offer> res = Optional.empty();
            if(userDetails.getRole().equals("candidate")){
                res= repository.findByOfferIdAndCandidateId(updateReq.offerId(),userDetails.getCandidateDetails().getCandidateId());
            }else if(userDetails.getRole().equals("recruiter")){
                res= repository.findByOfferIdAndRecruiterId(updateReq.offerId(),userDetails.getRecruiterDetails().getRecruiterId());
            }

            if (res.isPresent()) {
                var prevData = res.get();
                prevData.setStatus(updateReq.status());
                prevData.setStatusChangeNote(updateReq.statusChangeNote());
                //dont have to call save method, transactional annotation update the db for us if modified
//            Candidate updateResponse=candidateRepository.save(prevData);
                responseDTO.setCode(CodeList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                return responseDTO;
            } else {
                responseDTO.setCode(CodeList.RSP_ERROR);
                responseDTO.setMessage("Not Found!");
                responseDTO.setErrors(errors);
            }


        } catch (Exception e) {
            e.printStackTrace();
            errors.put("error", "Error Occurred!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }


        return responseDTO;
    }

}
