package com.gym.GoldenGym.services.imp;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gym.GoldenGym.dtos.Coordinates;
import com.gym.GoldenGym.dtos.ResponseDto;
import com.gym.GoldenGym.dtos.StoreDto;
import com.gym.GoldenGym.entities.Order;
import com.gym.GoldenGym.entities.SettingsEntity;
import com.gym.GoldenGym.entities.StoreLocation;
import com.gym.GoldenGym.repos.OrderRepo;
import com.gym.GoldenGym.repos.SettingsRepo;
import com.gym.GoldenGym.repos.StoreLocationRepo;
import com.gym.GoldenGym.services.StoreServices;
import com.gym.GoldenGym.utils.LocationsUtils;
import com.gym.GoldenGym.utils.ObjectHelper;
import com.gym.GoldenGym.utils.Username;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class StoreServicesImp implements StoreServices {
    private final StoreLocationRepo storeLocationRepo;
    private final OrderRepo orderRepo;
    private final SettingsRepo settingsRepo;

    @Override
    public ResponseDto getAllStores() {
        log.info("Getting all stores => User responsibleL: {} ", Username.getUsername());
        return new ResponseDto(200, "Success", storeLocationRepo.findByDeletedFalse());
    }

    @Override
    public ResponseDto createStore(StoreDto storeDto) {
        log.info("Creating store => User responsibleL: {} ", Username.getUsername());
        StoreLocation store = storeDto.toStoreLocation();
        return new ResponseDto(200, "Success", storeLocationRepo.save(store));
    }

    @Override
    public ResponseDto updateStore(Long storeId, StoreDto storeDto) {
        log.info("Updating store => User responsibleL: {} ", Username.getUsername());
        StoreLocation oldStore = storeLocationRepo.findById(storeId).orElse(null);
        if (oldStore == null) {
            log.info("Store not found");
            return new ResponseDto(400, "Store not found");
        }
        StoreLocation store = storeDto.toStoreLocation();
        store.setId(storeId);
        return new ResponseDto(200, "Success", storeLocationRepo.save(store));
    }

    @Override
    public ResponseDto getPreferedStoresForDelivery(Long orderId) {
        log.info("Getting prefered stores for delivery => User responsibleL: {} ", Username.getUsername());
        Order order = orderRepo.findById(orderId).orElse(null);
        if (order == null) {
            log.info("Order not found");
            return new ResponseDto(400, "Order not found");
        }
        List<StoreLocation> stores = storeLocationRepo.findByDeletedFalse();

        if (stores.isEmpty()) {
            log.info("No stores found");
            return new ResponseDto(400, "No stores found");
        }

        Map<Double, StoreLocation> map = new HashMap<>();

        for (StoreLocation store : stores) {
            double distance = LocationsUtils.getDistance(ObjectHelper.getDouble(order.getLatitude()),
                    ObjectHelper.getDouble(order.getLongitude()), ObjectHelper.getDouble(store.getLatitude()),
                    ObjectHelper.getDouble(store.getLongitude()));
            map.put(distance, store);
        }

        Map<Double, StoreLocation> sortedMap = map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return new ResponseDto(200, "Success", sortedMap.values());
    }

    @Override
    public ResponseDto canMakeOrders(Coordinates coordinates) {
        SettingsEntity settings = settingsRepo.findById(1L).orElse(null);

        if (settings == null) {
            log.info("Settings not found");
            return new ResponseDto(200, "Can make orders", true);
        }

        if (!settings.isCheckClientDistance()) {
            log.info("Settings not found");
            return new ResponseDto(200, "Can make orders", true);
        }

        List<StoreLocation> stores = storeLocationRepo.findByDeletedFalse();

        if (stores.isEmpty()) {
            log.info("No stores found");
            return new ResponseDto(200, "Can make orders", true);
        }

        boolean hasStoreWithinDistance = stores.stream().anyMatch(store -> {
            boolean withinDistance = LocationsUtils.isLocationWithinRadius(
                    ObjectHelper.getDouble(coordinates.getLatitude()),
                    ObjectHelper.getDouble(coordinates.getLongitude()), ObjectHelper.getDouble(store.getLatitude()),
                    ObjectHelper.getDouble(store.getLongitude()), settings.getClientDistanceMax());
            return withinDistance == true;
        });

        if (hasStoreWithinDistance) {
            log.info("Store found within distance");
            return new ResponseDto(200, "Can make orders", true);
        } else {
            log.info("No store within distance");
            return new ResponseDto(200, "No store within distance", false);
        }
    }

}
