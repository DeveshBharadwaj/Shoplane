package com.cfs.Ecomm.service;

import com.cfs.Ecomm.dto.OrderDto;
import com.cfs.Ecomm.dto.OrderItemDto;
import com.cfs.Ecomm.model.OrderItem;
import com.cfs.Ecomm.model.Orders;
import com.cfs.Ecomm.model.Product;
import com.cfs.Ecomm.model.User;
import com.cfs.Ecomm.repo.OrderRepository;
import com.cfs.Ecomm.repo.ProductRepository;
import com.cfs.Ecomm.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EmailService emailService;

    public OrderDto placeOrder(Long userId, Map<Long,Integer> productQuantities,double totalAmount,String paymentId)
    {
        User user=userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("user not found"));

        Orders order=new Orders();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus("PAID");
        order.setTotalAmount(totalAmount);
        order.setRazorpayPaymentId(paymentId);

        List<OrderItem> orderItems=new ArrayList<>();
        List<OrderItemDto> orderItemDtos=new ArrayList<>();

        for (Map.Entry<Long,Integer> entry: productQuantities.entrySet())
        {
            Product product=productRepository.findById(entry.getKey())
                    .orElseThrow(()->new RuntimeException("Product not found"));

            OrderItem orderItem=new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(entry.getValue());
            orderItems.add(orderItem);
            orderItemDtos.add(new OrderItemDto(product.getName(),product.getPrice(), entry.getValue()));
        }
        order.setOrderItems(orderItems);
        Orders saveOrder=orderRepository.save(order);
        if (user.getEmail() != null) {
            emailService.sendOrderVerificationEmail(
                    user.getEmail(),
                    user.getName(),
                    saveOrder.getId(),
                    saveOrder.getTotalAmount(),
                    paymentId
            );
        }
        return new OrderDto(saveOrder.getId(), saveOrder.getTotalAmount(), saveOrder.getStatus(), saveOrder.getOrderDate(),orderItemDtos);

    }

    public List<OrderDto> getAllOrders()
    {
        List<Orders> orders=orderRepository.findAllOrdersWithUsers();
        return orders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public OrderDto convertToDTO(Orders orders) {
        List<OrderItemDto> orderItems=orders.getOrderItems().stream()
                .map(item->new OrderItemDto(
                        item.getProduct().getName(),
                        item.getProduct().getPrice(),
                        item.getQuantity())).collect(Collectors.toList());
                return new OrderDto(
                        orders.getId(),
                        orders.getTotalAmount(),
                        orders.getStatus(),
                        orders.getOrderDate(),
                        orders.getUser()!=null ? orders.getUser().getName() : "unknown",
                        orders.getUser()!=null ? orders.getUser().getEmail() : "unknown",
                        orderItems
                );
    }

    public List<OrderDto> getOrderByUser(Long userId)
    {
       Optional<User> userOp= userRepository.findById(userId);
       if (userOp.isEmpty())
       {
           throw new RuntimeException("user not found");

       }
       User user=userOp.get();
       List<Orders> ordersList=orderRepository.findByUser(user);

       return ordersList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
