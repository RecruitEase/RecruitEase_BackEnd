package com.recruitease.payment_service.service;

import com.recruitease.payment_service.dto.PaymentRequest;
import com.recruitease.payment_service.dto.PaymentResponse;
import com.stripe.Stripe;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.secretKey}")
    private String secretKey;

    //stripe-api
    //productname,amount,quantity,currency
    //return sessionId,url
    public PaymentResponse checkoutPayment(PaymentRequest paymentRequest){
        Stripe.apiKey=secretKey;

        SessionCreateParams.LineItem.PriceData.ProductData productData=SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(paymentRequest.getName()).build();

        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(paymentRequest.getCurrency() == null ? "LKR" : paymentRequest.getCurrency())
                .setUnitAmount(paymentRequest.getAmount())
                .build();

        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(paymentRequest.getQuantity())
                .setPriceData(priceData)
                .build();

        SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/success")
                .setCancelUrl("http://localhost:3000/cancel")
                .addLineItem(lineItem)
                .build();

    }

}
