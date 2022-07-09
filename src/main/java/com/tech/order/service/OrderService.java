package com.tech.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tech.order.common.Payment;
import com.tech.order.common.TranactionRequest;
import com.tech.order.common.TransactionResponse;
import com.tech.order.entity.Order;
import com.tech.order.repository.OrderRepository;
@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private RestTemplate restTemplate; 
	 
	public TransactionResponse saveOrder(TranactionRequest request) {	
		String response = "";
		Order order = request.getOrder();
		Payment payment = request.getPayment();
		payment.setOrderId(order.getId());
		payment.setAmount(order.getPrice());
		
		Payment paymentResponse = restTemplate.postForObject("http://PAYMENT-SERVICE//payment//doPayment", payment, Payment.class);
		System.out.println(paymentResponse.getPaymentStatus());
		response= paymentResponse.getPaymentStatus().equals("success")?"Payment Processing Successful and order place":"failed transaction";
		
		
		orderRepository.save(order);
		return new TransactionResponse(order, paymentResponse.getAmount(),paymentResponse.getTransationId(),response);
	}

}
