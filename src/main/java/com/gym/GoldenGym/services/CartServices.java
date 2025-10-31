package com.gym.GoldenGym.services;

import com.gym.GoldenGym.dtos.AddToCart;
import com.gym.GoldenGym.dtos.ResponseDto;
import com.gym.GoldenGym.dtos.reqdtos.CartItemsReqDto;

public interface CartServices {
    public ResponseDto fetchCartItems(CartItemsReqDto cartItemReq);

    public ResponseDto addToCart(AddToCart addToCart);

    public ResponseDto removeFromCart(Long cartItemId);
}
