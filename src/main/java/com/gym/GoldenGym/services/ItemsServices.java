package com.gym.GoldenGym.services;

import com.gym.GoldenGym.dtos.AdminInventoryAdjustDto;
import com.gym.GoldenGym.dtos.InventoryAdjustDto;
import com.gym.GoldenGym.dtos.ItemDto;
import com.gym.GoldenGym.dtos.ItemVariantDto;
import com.gym.GoldenGym.dtos.MultiVariantsEntry;
import com.gym.GoldenGym.dtos.ResponseDto;

public interface ItemsServices {
    public ResponseDto fetchItems();

    public ResponseDto createItem(ItemDto itemDto);

    public ResponseDto creteVariants(MultiVariantsEntry multiVariantsEntry);

    public ResponseDto deleteItem(Long itemId);

    public ResponseDto updateItem(ItemDto itemDto);

    public ResponseDto updateVariant(ItemVariantDto itemVariantDto);

    public ResponseDto deleteVariant(Long variantId);

    public ResponseDto fetchItemVariantsFromVariant(Long variantId);

    public ResponseDto fetchItemVariants(Long itemId);

    public ResponseDto adjustInventory(InventoryAdjustDto inventoryAdjustDto);

    public ResponseDto adminAdjustInventory(AdminInventoryAdjustDto adminInventoryAdjustDto);
}
