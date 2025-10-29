package com.gym.GoldenGym.services.imp;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gym.GoldenGym.dtos.AdminInventoryAdjustDto;
import com.gym.GoldenGym.dtos.CategoryDto;
import com.gym.GoldenGym.dtos.InventoryAdjustDto;
import com.gym.GoldenGym.dtos.ItemDto;
import com.gym.GoldenGym.dtos.ItemVariantDto;
import com.gym.GoldenGym.dtos.MultiCategoryDto;
import com.gym.GoldenGym.dtos.ResponseDto;
import com.gym.GoldenGym.dtos.InventoryAdjustDto.Action;
import com.gym.GoldenGym.dtos.reqdtos.ItemFetch;
import com.gym.GoldenGym.dtos.reqdtos.VariantRequestDto;
import com.gym.GoldenGym.entities.Category;
import com.gym.GoldenGym.entities.FileEntity;
import com.gym.GoldenGym.entities.Item;
import com.gym.GoldenGym.entities.ItemVariant;
import com.gym.GoldenGym.entities.StoreInventory;
import com.gym.GoldenGym.entities.StoreLocation;
import com.gym.GoldenGym.entities.User;
import com.gym.GoldenGym.repos.CategoryRepo;
import com.gym.GoldenGym.repos.CriteriaRepo;
import com.gym.GoldenGym.repos.ItemRepo;
import com.gym.GoldenGym.repos.ItemVariantRepo;
import com.gym.GoldenGym.repos.StoreInventoryRepo;
import com.gym.GoldenGym.repos.StoreLocationRepo;
import com.gym.GoldenGym.repos.UserRepo;
import com.gym.GoldenGym.services.FilesServices;
import com.gym.GoldenGym.services.ItemsServices;
import com.gym.GoldenGym.utils.Username;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemsServicesImp implements ItemsServices {
    private final FilesServices filesServices;
    private final ItemRepo itemRepo;
    private final ItemVariantRepo itemVariantRepo;
    private final StoreLocationRepo storeLocationRepo;
    private final StoreInventoryRepo inventory;
    private final UserRepo userRepo;
    private final CriteriaRepo criteriaRepo;
    private final CategoryRepo categoryRepo;

    @Override
    public ResponseDto fetchItems(ItemFetch itemFetch) {
        User user = userRepo.findByEmail(Username.getUsername()).orElse(null);
        log.info("User: {}", user.getEmail());
        return new ResponseDto(200, "Success", criteriaRepo.fetchItems(itemFetch));
    }

    @Override
    public ResponseDto fetchCategories() {
        User user = userRepo.findByEmail(Username.getUsername()).orElse(null);
        log.info("User: {}", user.getEmail());
        return new ResponseDto(200, "Success", categoryRepo.findAll());
    }

    @Override
    public ResponseDto createCategory(MultiCategoryDto multiCategoryDto) {
        User user = userRepo.findByEmail(Username.getUsername()).orElse(null);
        log.info("User: {}", user.getEmail());
        categoryRepo.saveAll(multiCategoryDto.getCategories().stream().map(CategoryDto::toCategory).toList());
        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto createItem(ItemDto itemDto) {
        User user = userRepo.findByEmail(Username.getUsername()).orElse(null);
        log.info("User: {}", user.getEmail());
        Category category = categoryRepo.findById(itemDto.getCategoryId()).orElse(null);
        if (category == null) {
            log.info("Category could not be found");
            return new ResponseDto(400, "Category not found");
        }

        Item item = itemDto.toItem(category);

        itemRepo.save(item);
        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto deleteItem(Long itemId) {
        User user = userRepo.findByEmail(Username.getUsername()).orElse(null);
        log.info("User: {}", user.getEmail());
        Item item = itemRepo.findById(itemId).orElse(null);
        if (item == null) {
            log.info("Item could not be found");
            return new ResponseDto(400, "Item not found");
        }
        item.setDeleted(true);
        log.info("Deleting item");
        itemRepo.save(item);
        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto updateItem(Long itemId, ItemDto itemDto) {
        User user = userRepo.findByEmail(Username.getUsername()).orElse(null);
        log.info("User: {}", user.getEmail());
        Item item = itemRepo.findById(itemId).orElse(null);
        if (item == null) {
            log.info("Item could not be found");
            return new ResponseDto(400, "Item not found");
        }
        Category category = categoryRepo.findById(itemDto.getCategoryId()).orElse(null);
        if (category == null) {
            log.info("Category could not be found");
            return new ResponseDto(400, "Category not found");
        }
        item.setCategory(category);
        item.setItemName(itemDto.getItemName());
        item.setItemDescription(itemDto.getItemDescription());
        item.setManufacturer(itemDto.getManufacturer());
        log.info("Updating item");
        itemRepo.save(item);
        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto createVariant(List<MultipartFile> files, ItemVariantDto itemVariantDto) {
        User user = userRepo.findByEmail(Username.getUsername()).orElse(null);

        log.info("User: {}", user.getEmail());
        try {
            Set<FileEntity> images = filesServices.fileUploads(files, "itemVariant").stream()
                    .collect(Collectors.toSet());
            ItemVariant itemVariant = itemVariantDto.toItemVariant(images);
            itemVariantRepo.save(itemVariant);
            return new ResponseDto(200, "Success");
        } catch (Exception e) {
            log.info("Error: {}", e.getMessage());
            return new ResponseDto(400, "Error, could not create variant");
        }
    }

    @Override
    public ResponseDto updateVariant(Long varantId, ItemVariantDto itemVariantDto) {
        User user = userRepo.findByEmail(Username.getUsername()).orElse(null);
        log.info("User: {}", user.getEmail());
        ItemVariant itemVariant = itemVariantRepo.findById(varantId).orElse(null);
        if (itemVariant == null) {
            log.info("Item Variant could not be found");
            return new ResponseDto(400, "Item Variant not found");
        }
        itemVariant.setVariantPrice(itemVariantDto.getOfferPrice());
        itemVariant.setMetricMeasure(itemVariantDto.getMetricMeasure());
        itemVariant.setOfferStartDateTime(itemVariantDto.getOfferStartDateTime());
        itemVariant.setOfferEndDateTime(itemVariantDto.getOfferEndDateTime());
        itemVariant.setOfferPrice(itemVariantDto.getOfferPrice());
        itemVariantRepo.save(itemVariant);
        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto deleteVariant(Long variantId) {
        User user = userRepo.findByEmail(Username.getUsername()).orElse(null);
        log.info("User: {}", user.getEmail());
        ItemVariant itemVariant = itemVariantRepo.findById(variantId).orElse(null);
        if (itemVariant == null) {
            log.info("Item Variant could not be found");
            return new ResponseDto(400, "Item Variant not found");
        }
        itemVariant.setDeleted(true);
        itemVariantRepo.save(itemVariant);
        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto fetchItemVariantsFromVariant(Long variantId) {
        User user = userRepo.findByEmail(Username.getUsername()).orElse(null);
        log.info("User: {}", user.getEmail());
        ItemVariant itemVariant = itemVariantRepo.findById(variantId).orElse(null);
        if (itemVariant == null) {
            log.info("Item Variant could not be found");
            return new ResponseDto(400, "Item Variant not found");
        }
        Item item = itemVariant.getItem();

        List<ItemVariant> itemVariants = itemVariantRepo.findByItemAndDeletedFalse(item);

        return new ResponseDto(200, "Success", itemVariants);
    }

    @Override
    public ResponseDto fetchItemVariants(Long itemId) {
        User user = userRepo.findByEmail(Username.getUsername()).orElse(null);
        log.info("User: {}", user.getEmail());
        Item item = itemRepo.findById(itemId).orElse(null);
        if (item == null) {
            log.info("Item could not be found");
            return new ResponseDto(400, "Item not found");
        }
        List<ItemVariant> itemVariants = itemVariantRepo.findByItemAndDeletedFalse(item);
        return new ResponseDto(200, "Success", itemVariants);
    }

    @Override
    public ResponseDto adjustInventory(InventoryAdjustDto inventoryAdjustDto) {
        User user = userRepo.findByEmail(Username.getUsername()).orElse(null);
        log.info("User: {}", user.getEmail());

        // ensure the user is assigned a store location
        StoreLocation storeLocation = user.getStoreLocation();
        if (storeLocation == null) {
            log.info("Store Location could not be found");
            return new ResponseDto(400, "Store Location not found");
        }

        ItemVariant itemVariant = itemVariantRepo.findById(inventoryAdjustDto.getVariantId()).orElse(null);
        if (itemVariant == null) {
            log.info("Item Variant could not be found");
            return new ResponseDto(400, "Item Variant not found");
        }

        StoreInventory storeInventory = inventory.findByItemVariantAndStoreLocation(itemVariant, storeLocation);
        if (storeInventory == null) {
            log.info("Store Inventory could not be found");
            // create new inventory record
            storeInventory = new StoreInventory();
            storeInventory.setItemVariant(itemVariant);
            storeInventory.setStoreLocation(storeLocation);
            storeInventory.setQuantity(inventoryAdjustDto.getQuantity());
            inventory.save(storeInventory);
            return new ResponseDto(200, "Success");
        }

        storeInventory.setQuantity(storeInventory.getQuantity() + inventoryAdjustDto.getQuantity());
        inventory.save(storeInventory);
        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto adminAdjustInventory(AdminInventoryAdjustDto adminInventoryAdjustDto) {
        User user = userRepo.findByEmail(Username.getUsername()).orElse(null);
        log.info("User: {}", user.getEmail());
        StoreLocation storeLocation = storeLocationRepo.findById(adminInventoryAdjustDto.getStoreId()).orElse(null);
        if (storeLocation == null) {
            log.info("Store Location could not be found");
            return new ResponseDto(400, "Store Location not found");
        }

        ItemVariant itemVariant = itemVariantRepo.findById(adminInventoryAdjustDto.getVariantId()).orElse(null);
        if (itemVariant == null) {
            log.info("Item Variant could not be found");
            return new ResponseDto(400, "Item Variant not found");
        }

        StoreInventory storeInventory = inventory.findById(adminInventoryAdjustDto.getStoreId()).orElse(null);
        if (storeInventory == null) {
            log.info("Store Inventory could not be found");
            // create new inventory record
            storeInventory = new StoreInventory();
            storeInventory.setItemVariant(itemVariant);
            storeInventory.setStoreLocation(storeLocation);
            storeInventory.setQuantity(adminInventoryAdjustDto.getQuantity());
            inventory.save(storeInventory);
            return new ResponseDto(200, "Success");
        }
        if (adminInventoryAdjustDto.getAction().equals(Action.INCREASE)) {
            storeInventory.setQuantity(storeInventory.getQuantity() + adminInventoryAdjustDto.getQuantity());
            inventory.save(storeInventory);
        } else {
            if (storeInventory.getQuantity() < adminInventoryAdjustDto.getQuantity()) {
                return new ResponseDto(400, "Not enough inventory");
            }
            storeInventory.setQuantity(storeInventory.getQuantity() - adminInventoryAdjustDto.getQuantity());
            inventory.save(storeInventory);
        }

        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto fetchVariants(VariantRequestDto itemRequestDto) {
        User user = userRepo.findByEmail(Username.getUsername()).orElse(null);
        log.info("User: {}", user.getEmail());
        return new ResponseDto(200, "Success", criteriaRepo.fetchItems(itemRequestDto));
    }

}
