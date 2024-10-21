package com.recruitease.application_service.DTO;

import com.recruitease.application_service.entity.Offer;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record OfferUpdateRequest(
        @NotNull(message = "OfferId is required")
        @NotEmpty(message = "OfferId cannot be empty")
        String offerId,
        @NotNull(message = "status is required")
        Offer.OfferStatus status,
        String statusChangeNote

) {

}
