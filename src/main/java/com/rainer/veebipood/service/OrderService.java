package com.rainer.veebipood.service;

import com.rainer.veebipood.entity.Order;
import com.rainer.veebipood.entity.OrderRow;
import com.rainer.veebipood.entity.Person;
import com.rainer.veebipood.entity.Product;
import com.rainer.veebipood.models.EveryPayBody;
import com.rainer.veebipood.models.EveryPayCheck;
import com.rainer.veebipood.models.EveryPayResponse;
import com.rainer.veebipood.models.PaymentLink;
import com.rainer.veebipood.repository.OrderRepository;
import com.rainer.veebipood.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Value("${everypay.username}")
    private String everypayUsername;

    @Value("${everypay.password}")
    private String everypayPassword;

    @Value("${everypay.base-url}")
    private String everypayBaseURL;

    @Value("${everypay.customer-url}")
    private String everypayCustomerURL;


    public Order addOrder(List<OrderRow> orderRows, String email) {
        Order order = new Order();
        //
        order.setCreated(new Date());
        order.setTotalSum(calculateTotalSum(orderRows));

        Person person = new Person();
        // email primary key
        person.setEmail(email);
        order.setPerson(person);

        order.setOrderRows(orderRows);
        return orderRepository.save(order);

    }

    private double calculateTotalSum(List<OrderRow> orderRows) {
        double sum = 0;
        for (OrderRow row : orderRows) {
            Product product = productRepository.findById(row.getProduct().getName()).orElseThrow();
            sum += row.getQuantity() * product.getPrice();
        }
        return sum;
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public PaymentLink getPaymentLink(Order order) {
        String url = everypayBaseURL +"/payments/oneoff";

        EveryPayBody body = new EveryPayBody();
        body.setAccount_name("EUR3D1");
        body.setNonce("165das12" + ZonedDateTime.now().toString() + Math.random());
        body.setTimestamp(ZonedDateTime.now().toString());
        body.setAmount(order.getTotalSum());
        body.setOrder_reference(order.getId().toString());
        body.setCustomer_url(everypayCustomerURL);
        body.setApi_username(everypayUsername);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(everypayUsername,everypayPassword);

        HttpEntity<EveryPayBody> entity = new HttpEntity<>(body, headers);

        ResponseEntity<EveryPayResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, EveryPayResponse.class);
        PaymentLink link = new PaymentLink();
        link.setLink(response.getBody().getPayment_link());
        return link;
    }

    public Boolean checkPayment(String paymentReference) {
        String url = everypayBaseURL + "/payments/" + paymentReference
                + "?api_username=" + everypayUsername
                + "&detailed=false";

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(everypayUsername, everypayPassword);

        HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);

        ResponseEntity<EveryPayCheck> response = restTemplate.exchange(url, HttpMethod.GET, entity, EveryPayCheck.class);

        EveryPayCheck responseBody = response.getBody();
        if (responseBody == null) {
            return false;
        }

        Long orderId = Long.valueOf(responseBody.getOrder_reference());
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setPayment(responseBody.getPayment_state());
        orderRepository.save(order);

        return response.getBody().getPayment_state().equals("settled");

    }
}
