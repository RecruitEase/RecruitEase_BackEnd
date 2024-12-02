package com.recruitease.payment_service.controller;

import com.recruitease.payment_service.dto.PaymentRequest;
import com.recruitease.payment_service.dto.ResponseDTO;
import com.recruitease.payment_service.service.StripeService;
import com.recruitease.payment_service.util.CodeList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final StripeService service;

    @PostMapping("/checkout")
    public ResponseEntity<ResponseDTO> checkoutProducts(@RequestBody PaymentRequest request) {
        ResponseDTO res= service.checkoutPayment(request);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res, HttpStatus.OK);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }


}
