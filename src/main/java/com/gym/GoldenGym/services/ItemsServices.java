package com.gym.GoldenGym.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.gym.GoldenGym.dtos.AdminInventoryAdjustDto;
import com.gym.GoldenGym.dtos.InventoryAdjustDto;
import com.gym.GoldenGym.dtos.ItemDto;
import com.gym.GoldenGym.dtos.ItemVariantDto;
import com.gym.GoldenGym.dtos.ResponseDto;

public interface ItemsServices {
    public ResponseDto fetchItems();

    public ResponseDto fetchCategories();

    public ResponseDto createCategory(MultipartFile file, String fileName);

    public ResponseDto createItem(ItemDto itemDto);

    public ResponseDto deleteItem(Long itemId);

    public ResponseDto updateItem(ItemDto itemDto);

    public ResponseDto createVariant(List<MultipartFile> files, ItemVariantDto itemVariantDto);

    public ResponseDto updateVariant(ItemVariantDto itemVariantDto);

    public ResponseDto deleteVariant(Long variantId);

    public ResponseDto fetchItemVariantsFromVariant(Long variantId);

    public ResponseDto fetchItemVariants(Long itemId);

    public ResponseDto adjustInventory(InventoryAdjustDto inventoryAdjustDto);

    public ResponseDto adminAdjustInventory(AdminInventoryAdjustDto adminInventoryAdjustDto);
}
