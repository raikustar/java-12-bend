package com.rainer.veebipood.controller;


import com.rainer.veebipood.entity.Order;
import com.rainer.veebipood.entity.OrderRow;
import com.rainer.veebipood.entity.Person;
import com.rainer.veebipood.models.PaymentLink;
import com.rainer.veebipood.repository.OrderRepository;
import com.rainer.veebipood.service.OrderService;
import com.rainer.veebipood.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    private PersonService personService;


    // Õiges arenduses ei tagasta pärast tellimuse lisamist kõiki tellimusi
    // localhost:8080/orders?email=bla@gmail.com
    @PostMapping("orders")
    public PaymentLink addOrder(@RequestBody List<OrderRow> orderRows) {
        // hiljem võtame emaili token küljest (pärast sisselogimist)
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Order order = orderService.addOrder(orderRows, email);
        return orderService.getPaymentLink(order);
    }

    // Failed  --> ?order_reference=1045102&payment_reference=35d46a523446e84f0a925aad7e75cf2a4b44d668ebbdf559ea2a632071eccb2d
    // Success --> ?order_reference=1045101&payment_reference=7fb93e6dc481dcef895546117b9b9c9bc22ac3df9a38f193743ef01333256d7b

    @GetMapping("check-payment")
    public Boolean checkPayment(@RequestParam String paymentReference) {
        return orderService.checkPayment(paymentReference);
    }


    @GetMapping("orders")
    public List<Order> getOrders() {
        return orderService.getOrders();
    }
}
