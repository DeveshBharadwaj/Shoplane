package com.cfs.Ecomm.controller;

import com.cfs.Ecomm.dto.PaymentRequestDto;
import com.cfs.Ecomm.service.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@CrossOrigin("*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // 💳 Initiates payment and returns the raw Razorpay Order ID to the frontend
    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequestDto paymentRequest) {
        try {
            String razorpayOrderId = paymentService.createPayment(paymentRequest);
            return ResponseEntity.ok(razorpayOrderId);
        } catch (RazorpayException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error communicating with Razorpay: " + e.getMessage());
        }
    }
}
