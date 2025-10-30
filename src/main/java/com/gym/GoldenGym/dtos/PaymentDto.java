package com.gym.GoldenGym.dtos;

import com.gym.GoldenGym.entities.Order;
import com.gym.GoldenGym.entities.PaymentsEntity;
import com.gym.GoldenGym.entities.ServiceOrderEntity;

import lombok.Data;

@Data
public class PaymentDto {
    private Long orderId;
    private String paymentMethod;
    private String paymentRef;

    public PaymentsEntity toEntity(Order order){
        PaymentsEntity payment = new PaymentsEntity();
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentRef(paymentRef);
        payment.setAmount(order.getTotalAmount());
        return payment;
    }

    public PaymentsEntity toEntity(ServiceOrderEntity serviceOrderEntity){
        PaymentsEntity payment = new PaymentsEntity();
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentRef(paymentRef);
        payment.setAmount(serviceOrderEntity.getService().getServicePrice());
        return payment;
    }
}
