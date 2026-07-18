package com.cfs.Ecomm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;


    public void sendOrderVerificationEmail(String toEmail, String customerName, Long orderId, double amount, String paymentId) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("no-reply@ecommstore.com");
            message.setTo(toEmail);
            message.setSubject("Payment Verified! Order Confirmation #" + orderId);

            String emailBody = "Dear " + customerName + ",\n\n" +
                    "Thank you for your order! Your payment has been successfully verified.\n\n" +
                    "--- Transaction Receipt ---\n" +
                    "Order Number: " + orderId + "\n" +
                    "Razorpay Payment ID: " + paymentId + "\n" +
                    "Total Amount Paid: Rs. " + amount + "\n\n" +
                    "We are processing your items and will notify you as soon as they ship.\n\n" +
                    "Warm regards,\n" +
                    "E-comm Team Store";

            message.setText(emailBody);
            mailSender.send(message);
            System.out.println("Verification email dispatched to: " + toEmail);
        } catch (Exception e) {
            System.err.println("Error sending notification email: " + e.getMessage());
        }
    }
}
