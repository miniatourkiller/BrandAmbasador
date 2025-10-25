package com.gym.GoldenGym.services;

import com.gym.GoldenGym.dtos.OrderCart;
import com.gym.GoldenGym.dtos.OrderDto;
import com.gym.GoldenGym.dtos.ResponseDto;
import com.gym.GoldenGym.dtos.reqdtos.OrdersReq;

public interface OrderServices {
    public ResponseDto makeOrderDirect(OrderDto orderDto);

    public ResponseDto makeOrderFromCart(OrderCart orderCart);

    public ResponseDto getAllOrders(OrdersReq ordersReq);

    public ResponseDto getStoreOrders(OrdersReq ordersReq);

    public ResponseDto assignOrderToStore(Long orderId);

    public ResponseDto deleteOrder(Long orderId);

    public ResponseDto markOrderPaidManualy(Long orderId);

    public ResponseDto markOrderDelivered(Long orderId);
}