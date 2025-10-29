package com.gym.GoldenGym.services;

import com.gym.GoldenGym.dtos.AssignStoreDto;
import com.gym.GoldenGym.dtos.OrderCart;
import com.gym.GoldenGym.dtos.OrderDto;
import com.gym.GoldenGym.dtos.ResponseDto;
import com.gym.GoldenGym.dtos.ServiceAssignStoreDto;
import com.gym.GoldenGym.dtos.ServiceOrderDto;
import com.gym.GoldenGym.dtos.ServiceOrderScheduleDto;
import com.gym.GoldenGym.dtos.reqdtos.OrdersReq;

public interface OrderServices {
    public ResponseDto makeOrderDirect(OrderDto orderDto);

    public ResponseDto makeOrderFromCart(OrderCart orderCart);

    public ResponseDto getAllOrders(OrdersReq ordersReq);

    public ResponseDto getStoreOrders(OrdersReq ordersReq);

    public ResponseDto assignOrderToStore(AssignStoreDto assignStoreDto);

    public ResponseDto makeServiceOrder(ServiceOrderDto serviceOrderDto);

    public ResponseDto scheduleServiceOrder(ServiceOrderScheduleDto serviceOrderDto);

    public ResponseDto assignServiceOrderToStore(ServiceAssignStoreDto assignStoreDto);

    public ResponseDto deleteOrder(Long orderId);

    public ResponseDto markOrderPaidManualy(Long orderId);

    public ResponseDto markOrderDelivered(Long orderId);

    public ResponseDto markServiceCompleted(Long orderId);

    public ResponseDto markServicePaidManually(Long orderId);
}