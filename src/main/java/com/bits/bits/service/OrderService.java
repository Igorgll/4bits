package com.bits.bits.service;

import com.bits.bits.model.CartItem;
import com.bits.bits.model.Orders;
import com.bits.bits.model.ShoppingCart;
import com.bits.bits.model.UserModel;
import com.bits.bits.repository.CartItemRepository;
import com.bits.bits.repository.OrderRepository;
import com.bits.bits.repository.ShoppingCartRepository;
import com.bits.bits.util.OrderStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional
    public Orders createOrder(Long shoppingCartId, UserModel user) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId)
                .orElseThrow(() -> new RuntimeException("Shopping Cart not found."));

        if (shoppingCart.getItems().isEmpty()) {
            throw new RuntimeException("Shopping Cart is empty");
        }

        Orders order = new Orders();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.AGUARDANDO_PAGAMENTO);
        double totalAmount = shoppingCart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        order.setTotalAmount(totalAmount);

        Orders savedOrder = orderRepository.save(order);

        for (CartItem item : shoppingCart.getItems()) {
            item.setOrder(order);
            cartItemRepository.save(item);
        }

        // clear shoppingCart
        shoppingCart.getItems().clear();
        shoppingCartRepository.save(shoppingCart);

        return savedOrder;
    }

    public void updateOrderStatus(Long orderId, OrderStatus status) {
        Orders order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
    }
}

