package com.bits.bits.controller;

import com.bits.bits.model.Orders;
import com.bits.bits.model.UserModel;
import com.bits.bits.repository.OrderRepository;
import com.bits.bits.repository.UserRepository;
import com.bits.bits.service.OrderService;
import com.bits.bits.util.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public List<Orders> getAllOrders(){ return orderRepository.findAll(); }

    @GetMapping("/{orderId}")
    public Optional<Orders> getOrderById(@PathVariable Long orderId){ return orderRepository.findById(orderId); }

    @PostMapping("/create")
    public Orders createOrder(@RequestParam Long userId, @RequestParam Long shoppingCartId) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: {}" + userId));
        return orderService.createOrder(shoppingCartId, user);
    }

    @PutMapping("/updateStatus/{orderId}")
    public void updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus status) {
        orderService.updateOrderStatus(orderId, status);
    }
}

