package com.gym.GoldenGym.services;

import com.gym.GoldenGym.dtos.AssignStoreDto;
import com.gym.GoldenGym.dtos.DeliveryNotes;
import com.gym.GoldenGym.dtos.OrderCart;
import com.gym.GoldenGym.dtos.OrderDto;
import com.gym.GoldenGym.dtos.PaymentDto;
import com.gym.GoldenGym.dtos.ResponseDto;
import com.gym.GoldenGym.dtos.ServiceAssignStoreDto;
import com.gym.GoldenGym.dtos.ServiceOrderDto;
import com.gym.GoldenGym.dtos.ServiceOrderScheduleDto;
import com.gym.GoldenGym.dtos.reqdtos.OrdersReq;
import com.gym.GoldenGym.dtos.reqdtos.ServicesReq;

public interface OrderServices {
    public ResponseDto makeOrderDirect(OrderDto orderDto);

    public ResponseDto makeOrderFromCart(OrderCart orderCart);

    public ResponseDto getAllOrders(OrdersReq ordersReq, String who);

    public ResponseDto getAllServiceOrders(ServicesReq ordersReq, String who);

    public ResponseDto assignOrderToStore(AssignStoreDto assignStoreDto);

    public ResponseDto makeServiceOrder(ServiceOrderDto serviceOrderDto);

    public ResponseDto addDeliveryNotes(DeliveryNotes deliveryNotes);

    public ResponseDto scheduleServiceOrder(ServiceOrderScheduleDto serviceOrderDto);

    public ResponseDto assignServiceOrderToStore(ServiceAssignStoreDto assignStoreDto);

    public ResponseDto deleteOrder(Long orderId);

    public ResponseDto markOrderPaidManualy(PaymentDto paymentDto);

    public ResponseDto markOrderDelivered(Long orderId);

    public ResponseDto markServiceCompleted(Long orderId);

    public ResponseDto markServicePaidManually(PaymentDto paymentDto);

    public ResponseDto removeOrderPayment(Long orderId);

    public ResponseDto removeServiceOrderPayment(Long orderId);
}