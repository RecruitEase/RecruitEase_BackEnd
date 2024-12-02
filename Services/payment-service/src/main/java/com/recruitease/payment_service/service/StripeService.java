package com.recruitease.payment_service.service;

import com.recruitease.payment_service.dto.PaymentRequest;
import com.recruitease.payment_service.dto.PaymentResponse;
import com.recruitease.payment_service.dto.ResponseDTO;
import com.recruitease.payment_service.util.CodeList;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
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
    public ResponseDTO checkoutPayment(PaymentRequest paymentRequest){
        ResponseDTO responseDTO=new ResponseDTO();

        Stripe.apiKey=secretKey;

        SessionCreateParams.LineItem.PriceData.ProductData productData=SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(paymentRequest.getName()).build();

        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(paymentRequest.getCurrency() == null ? "LKR" : paymentRequest.getCurrency())
                .setUnitAmount(paymentRequest.getAmount())
                .setProductData(productData)
                .build();

        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(paymentRequest.getQuantity())
                .setPriceData(priceData)
                .build();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://recruitease.chathuralakshan.com/recruiter/subscription/success")
                .setCancelUrl("https://recruitease.chathuralakshan.com/recruiter/subscription/cancel")
                .addLineItem(lineItem)
                .build();

        Session session=null;
        try {
            session=Session.create(params);
            var obj=PaymentResponse.builder()
                    .sessionId(session.getId())
                    .sessionUrl(session.getUrl())
                    .build();

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(obj);
        } catch (StripeException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
        }

        return responseDTO;

    }

}
