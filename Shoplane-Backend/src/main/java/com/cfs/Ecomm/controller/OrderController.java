package com.cfs.Ecomm.controller;

import com.cfs.Ecomm.dto.OrderDto;
import com.cfs.Ecomm.model.OrderRequest;
import com.cfs.Ecomm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place/{userId}")
    public OrderDto placeOrder(@PathVariable Long userId, @RequestBody OrderRequest orderRequest)
    {
        return orderService.placeOrder(
                userId,
                orderRequest.getProductQuantities(),
                orderRequest.getTotalAmount(),
                orderRequest.getRazorpayPaymentId());
    }

    @GetMapping("/all-orders")
    public List<OrderDto> getAllOrders()
    {
        return orderService.getAllOrders();
    }

    @GetMapping("/user/{userId}")
    public List<OrderDto> getOrderByUser(@PathVariable Long userId)
    {
        return orderService.getOrderByUser(userId);
    }


}
