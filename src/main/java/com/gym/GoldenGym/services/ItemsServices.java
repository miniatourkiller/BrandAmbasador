package com.gym.GoldenGym.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.gym.GoldenGym.dtos.AdminInventoryAdjustDto;
import com.gym.GoldenGym.dtos.InventoryAdjustDto;
import com.gym.GoldenGym.dtos.ItemDto;
import com.gym.GoldenGym.dtos.ItemVariantDto;
import com.gym.GoldenGym.dtos.MultiCategoryDto;
import com.gym.GoldenGym.dtos.ResponseDto;
import com.gym.GoldenGym.dtos.reqdtos.ItemFetch;
import com.gym.GoldenGym.dtos.reqdtos.VariantRequestDto;

public interface ItemsServices {
    public ResponseDto fetchItems(ItemFetch itemFetch);

    public ResponseDto fetchCategories();

    public ResponseDto createCategory(MultiCategoryDto multiCategoryDto);

    public ResponseDto createItem(ItemDto itemDto);

    public ResponseDto deleteItem(Long itemId);

    public ResponseDto updateItem(Long itemId, ItemDto itemDto);

    public ResponseDto createVariant(List<MultipartFile> files, ItemVariantDto itemVariantDto);

    public ResponseDto fetchVariants(VariantRequestDto itemRequestDto);

    public ResponseDto updateVariant(Long variantId, ItemVariantDto itemVariantDto);

    public ResponseDto deleteVariant(Long variantId);

    public ResponseDto fetchItemVariantsFromVariant(Long variantId);

    public ResponseDto fetchItemVariants(Long itemId);

    public ResponseDto adjustInventory(InventoryAdjustDto inventoryAdjustDto);

    public ResponseDto adminAdjustInventory(AdminInventoryAdjustDto adminInventoryAdjustDto);
}
