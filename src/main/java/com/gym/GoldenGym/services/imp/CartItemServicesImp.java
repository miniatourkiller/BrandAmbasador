package com.gym.GoldenGym.services.imp;

import org.springframework.stereotype.Service;

import com.gym.GoldenGym.dtos.AddToCart;
import com.gym.GoldenGym.dtos.ResponseDto;
import com.gym.GoldenGym.dtos.reqdtos.CartItemsReqDto;
import com.gym.GoldenGym.entities.CartItemEntity;
import com.gym.GoldenGym.entities.ItemVariant;
import com.gym.GoldenGym.entities.User;
import com.gym.GoldenGym.repos.CartItemRepo;
import com.gym.GoldenGym.repos.CriteriaRepo;
import com.gym.GoldenGym.repos.ItemVariantRepo;
import com.gym.GoldenGym.repos.UserRepo;
import com.gym.GoldenGym.services.CartServices;
import com.gym.GoldenGym.utils.Username;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartItemServicesImp implements CartServices {
    private final CartItemRepo cartItemRepo;
    private final UserRepo userRepo;
    private final CriteriaRepo criteriaRepo;
    private final ItemVariantRepo itemVariantRepo;

    @Override
    public ResponseDto fetchCartItems(CartItemsReqDto cartItemReq) {
        log.info("Fetching cart items for user: {}", Username.getUsername());
        User user = userRepo.findByEmail(Username.getUsername()).orElse(null);

        if (user == null) {
            return new ResponseDto(400, "User not found");
        }
        return new ResponseDto(200, "Success", criteriaRepo.fetchCartItems(cartItemReq, user.getId()));
    }

    @Override
    public ResponseDto addToCart(AddToCart addToCart) {
        log.info("Adding item to cart for user: {}", Username.getUsername());
        User user = userRepo.findByEmail(Username.getUsername()).orElse(null);
        if (user == null) {
            return new ResponseDto(400, "User not found");
        }
        ItemVariant itemVariant = itemVariantRepo.findByIdAndDeletedFalse(addToCart.getVariantId()).orElse(null);
        if (itemVariant == null) {
            return new ResponseDto(400, "Item Variant not found");
        }
        CartItemEntity cartItem = new CartItemEntity();
        cartItem.setItemVariant(itemVariant);
        cartItem.setUnits(addToCart.getUnits());
        cartItem.setClient(user);

        cartItemRepo.save(cartItem);

        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto removeFromCart(Long cartItemId) {
        log.info("Removing item from cart for user: {}", Username.getUsername());
        User user = userRepo.findByEmail(Username.getUsername()).orElse(null);
        if (user == null) {
            return new ResponseDto(400, "User not found");
        }
        CartItemEntity cartItem = cartItemRepo.findByIdAndClientAndDeletedFalse(cartItemId, user).orElse(null);
        if (cartItem == null) {
            return new ResponseDto(400, "Cart Item not found");
        }
        cartItem.setDeleted(true);
        cartItemRepo.save(cartItem);
        return new ResponseDto(200, "Success");
    }

}
