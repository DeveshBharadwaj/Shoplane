package com.cfs.Ecomm.service;

import com.cfs.Ecomm.dto.PaymentRequestDto;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    public String createPayment(PaymentRequestDto paymentRequest) throws RazorpayException{
        RazorpayClient client=new RazorpayClient(keyId,keySecret);

        int amountInPaisa = (int) (paymentRequest.getAmount() * 100);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amountInPaisa);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "txn_" + System.currentTimeMillis());

        Order razorpayOrder=client.orders.create(orderRequest);

        return razorpayOrder.get("id").toString();

    }
}
