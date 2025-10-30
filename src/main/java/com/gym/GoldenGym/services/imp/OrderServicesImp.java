package com.gym.GoldenGym.services.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gym.GoldenGym.dtos.AssignStoreDto;
import com.gym.GoldenGym.dtos.DeliveryNotes;
import com.gym.GoldenGym.dtos.OrderCart;
import com.gym.GoldenGym.dtos.OrderDto;
import com.gym.GoldenGym.dtos.PaymentDto;
import com.gym.GoldenGym.dtos.RemovePaymentDto;
import com.gym.GoldenGym.dtos.ResponseDto;
import com.gym.GoldenGym.dtos.ServiceAssignStoreDto;
import com.gym.GoldenGym.dtos.ServiceOrderDto;
import com.gym.GoldenGym.dtos.ServiceOrderScheduleDto;
import com.gym.GoldenGym.dtos.reqdtos.OrdersReq;
import com.gym.GoldenGym.dtos.reqdtos.ServicesReq;
import com.gym.GoldenGym.entities.CartItemEntity;
import com.gym.GoldenGym.entities.ItemVariant;
import com.gym.GoldenGym.entities.Order;
import com.gym.GoldenGym.entities.PaymentsEntity;
import com.gym.GoldenGym.entities.ServiceEntity;
import com.gym.GoldenGym.entities.ServiceOrderEntity;
import com.gym.GoldenGym.entities.StoreInventory;
import com.gym.GoldenGym.entities.StoreLocation;
import com.gym.GoldenGym.entities.User;
import com.gym.GoldenGym.repos.CartItemRepo;
import com.gym.GoldenGym.repos.CriteriaRepo;
import com.gym.GoldenGym.repos.ItemVariantRepo;
import com.gym.GoldenGym.repos.OrderRepo;
import com.gym.GoldenGym.repos.PaymentsRepo;
import com.gym.GoldenGym.repos.ServiceOrderRepo;
import com.gym.GoldenGym.repos.ServicesRepo;
import com.gym.GoldenGym.repos.StoreInventoryRepo;
import com.gym.GoldenGym.repos.StoreLocationRepo;
import com.gym.GoldenGym.repos.UserRepo;
import com.gym.GoldenGym.services.OrderServices;
import com.gym.GoldenGym.utils.DateUtils;
import com.gym.GoldenGym.utils.Username;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServicesImp implements OrderServices {
    private final ItemVariantRepo itemVariantRepo;
    private final PaymentsRepo paymentsRepo;
    private final OrderRepo orderRepo;
    private final ServiceOrderRepo serviceOrderRepo;
    private final StoreInventoryRepo storeInventoryRepo;
    private final UserRepo userRepo;
    private final CartItemRepo cartItemRepo;
    private final CriteriaRepo criteriaRepo;
    private final StoreLocationRepo storeLocationRepo;
    private final ServicesRepo servicesRepo;

    @Override
    public ResponseDto makeOrderDirect(OrderDto orderDto) {
        User user = userRepo.findByEmail(Username.getUsername()).orElse(null);

        if (user == null) {
            log.info("Context user not found");
            return new ResponseDto(400, "No user found");
        }

        ItemVariant itemVariant = itemVariantRepo.findByIdAndDeletedFalse(orderDto.getVariantId()).orElse(null);

        if (itemVariant == null) {
            log.info("Item Variant not found");
            return new ResponseDto(400, "Item Variant not found");
        }

        if (orderDto.getCoordinates() == null) {
            log.info("No coordinates found");
            return new ResponseDto(400, "No coordinates found");
        }

        Order order = new Order();

        order.setItemVariant(itemVariant);
        order.setCustomer(user);
        order.setUnits(orderDto.getUnits());
        order.setLatitude(orderDto.getCoordinates().getLatitude());
        order.setLongitude(orderDto.getCoordinates().getLongitude());

        // check if there is an offer
        String offerStartDate = itemVariant.getOfferStartDateTime();
        String offerEndDate = itemVariant.getOfferEndDateTime();

        if (offerStartDate != null && offerEndDate != null) {
            if (!DateUtils.isDateInFuture(offerStartDate) && DateUtils.isDateInFuture(offerEndDate)) {
                log.info("There is an offer for variant item:>>>>> {} price:>>>> {}",
                        itemVariant.getItem().getItemName(), itemVariant.getOfferPrice());
                order.setTotalAmount(itemVariant.getOfferPrice() * orderDto.getUnits());
            } else {
                log.info("There is no offer for variant item:>>>>> {} price:>>>> {}",
                        itemVariant.getItem().getItemName(), itemVariant.getVariantPrice());
                order.setTotalAmount(itemVariant.getVariantPrice() * orderDto.getUnits());
            }
        }

        orderRepo.save(order);

        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto makeOrderFromCart(OrderCart orderCart) {
        User user = userRepo.findByEmail(Username.getUsername()).orElse(null);

        if (user == null) {
            log.info("Context user not found");
            return new ResponseDto(400, "No user found");
        }

        List<CartItemEntity> cartItems = cartItemRepo.findByIdsInAndDeletedFalse(orderCart.getCartItemIds());

        log.info("Found cart items : >>>>>{}", cartItems.size());

        if (cartItems.isEmpty()) {
            log.info("No cart items found");
            return new ResponseDto(400, "No cart items found");
        }

        if (orderCart.getCoordinates() == null) {
            log.info("No coordinates found");
            return new ResponseDto(400, "No coordinates found");
        }

        List<Order> newOrders = new ArrayList<>();

        for (CartItemEntity cartItem : cartItems) {
            Order order = new Order();

            order.setItemVariant(cartItem.getItemVariant());
            order.setCustomer(user);
            order.setUnits(cartItem.getUnits());
            order.setLatitude(orderCart.getCoordinates().getLatitude());
            order.setLongitude(orderCart.getCoordinates().getLongitude());

            ItemVariant itemVariant = cartItem.getItemVariant();

            // check if there is an offer
            String offerStartDate = itemVariant.getOfferStartDateTime();
            String offerEndDate = itemVariant.getOfferEndDateTime();

            if (offerStartDate != null && offerEndDate != null) {
                if (!DateUtils.isDateInFuture(offerStartDate) && DateUtils.isDateInFuture(offerEndDate)) {
                    log.info("There is an offer for variant item:>>>>> {} price:>>>> {}",
                            itemVariant.getItem().getItemName(), itemVariant.getOfferPrice());
                    order.setTotalAmount(itemVariant.getOfferPrice() * cartItem.getUnits());
                } else {
                    log.info("There is no offer for variant item:>>>>> {} price:>>>> {}",
                            itemVariant.getItem().getItemName(), itemVariant.getVariantPrice());
                    order.setTotalAmount(itemVariant.getVariantPrice() * cartItem.getUnits());
                }
            }

            newOrders.add(order);
        }

        log.info("Orders created: >>>>> {}", newOrders.size());

        orderRepo.saveAll(newOrders);

        List<CartItemEntity> deletedCartItmes = new ArrayList<>();

        // delete the cart items
        log.info("Deleting cart items");
        for (CartItemEntity cartItem : cartItems) {
            cartItem.setDeleted(true);

            deletedCartItmes.add(cartItem);
        }

        cartItemRepo.saveAll(deletedCartItmes);

        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto getAllOrders(OrdersReq ordersReq, String who) {
        log.info("Fetching orders: >>>>>>>>{}", Username.getUsername());
        Long storeId = null;
        Long customerId = null;

        User contextUser = userRepo.findByEmail(Username.getUsername()).orElse(null);

        if (contextUser == null) {
            log.info("No user found");
            return new ResponseDto(400, "No user found");
        }

        log.info("User: {}", contextUser.getEmail());

        switch (who) {
            case "staff":
                storeId = contextUser.getStoreLocation().getId();
                break;
            case "customer":
                customerId = contextUser.getId();
                break;

            default:
                break;
        }

        return new ResponseDto(200, "Success", criteriaRepo.fetchOrders(ordersReq, storeId, customerId));
    }

    @Override
    public ResponseDto getAllServiceOrders(ServicesReq ordersReq, String who) {
        log.info("Fetching orders: >>>>>>>>{}", Username.getUsername());
        Long storeId = null;
        Long customerId = null;

        User contextUser = userRepo.findByEmail(Username.getUsername()).orElse(null);

        if (contextUser == null) {
            log.info("No user found");
            return new ResponseDto(400, "No user found");
        }

        log.info("User: {}", contextUser.getEmail());

        switch (who) {
            case "staff":
                storeId = contextUser.getStoreLocation().getId();
                break;
            case "customer":
                customerId = contextUser.getId();
                break;

            default:
                break;
        }

        return new ResponseDto(200, "Success", criteriaRepo.fetchServiceOrders(ordersReq, storeId, customerId));
    }

    @Override
    public ResponseDto assignOrderToStore(AssignStoreDto assignStoreDto) {
        log.info("Assigning order to store: >>>>>>>>{}", Username.getUsername());
        User contextUser = userRepo.findByEmail(Username.getUsername()).orElse(null);
        if (contextUser == null) {
            log.info("No user found");
            return new ResponseDto(400, "No user found");
        }

        Order order = orderRepo.findById(assignStoreDto.getOrderId()).orElse(null);
        if (order == null) {
            log.info("Order not found");
            return new ResponseDto(400, "Order not found");
        }

        StoreLocation storeLocation = storeLocationRepo.findById(assignStoreDto.getStoreId()).orElse(null);
        if (storeLocation == null) {
            log.info("Store not found");
            return new ResponseDto(400, "Store not found");
        }

        order.setAssignedStore(storeLocation);

        orderRepo.save(order);

        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto makeServiceOrder(ServiceOrderDto serviceOrderDto) {
        log.info("Making service order: >>>>>>>>{}", Username.getUsername());

        User client = userRepo.findByEmail(Username.getUsername()).orElse(null);

        if (client == null) {
            log.info("No user found");
            return new ResponseDto(400, "No user found");
        }

        ServiceEntity service = servicesRepo.findByIdAndDeletedFalse(serviceOrderDto.getServiceId()).orElse(null);

        if (service == null) {
            log.info("Service not found");
            return new ResponseDto(400, "Service not found");
        }

        ServiceOrderEntity serviceOrder = new ServiceOrderEntity();

        serviceOrder.setService(service);
        serviceOrder.setClient(client);
        serviceOrder.setLatitude(serviceOrderDto.getCoordinates().getLatitude());
        serviceOrder.setLongitude(serviceOrderDto.getCoordinates().getLongitude());

        serviceOrderRepo.save(serviceOrder);

        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto addDeliveryNotes(DeliveryNotes deliveryNotes) {
        log.info("Adding delivery notes: >>>>>>>>{}", Username.getUsername());

        Order order = orderRepo.findByIdAndDeletedFalse(deliveryNotes.getOrderId()).orElse(null);

        if (order == null) {
            log.info("Order not found");
            return new ResponseDto(400, "Order not found");
        }

        order.setDeliveryNotes(deliveryNotes.getNotes());

        orderRepo.save(order);

        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto scheduleServiceOrder(ServiceOrderScheduleDto serviceOrderDto) {
        log.info("Making service order: >>>>>>>>{}", Username.getUsername());

        ServiceOrderEntity serviceOrder = serviceOrderRepo.findByIdAndDeletedFalse(serviceOrderDto.getOrderId())
                .orElse(null);

        if (serviceOrder == null) {
            log.info("Service order not found");
            return new ResponseDto(400, "Service order not found");
        }

        serviceOrder.setScheduledDateTime(serviceOrderDto.getScheduledDateTime());

        log.info("Service : {} scheduled for : {}", serviceOrder.getService().getServiceName(),
                serviceOrder.getScheduledDateTime());

        serviceOrderRepo.save(serviceOrder);

        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto assignServiceOrderToStore(ServiceAssignStoreDto assignStoreDto) {
        log.info("Assigning order to store: >>>>>>>>{}", Username.getUsername());

        ServiceOrderEntity serviceOrder = serviceOrderRepo.findByIdAndDeletedFalse(assignStoreDto.getOrderId())
                .orElse(null);

        if (serviceOrder == null) {
            log.info("Service order not found");
            return new ResponseDto(400, "Service order not found");
        }

        User staff = userRepo.findByIdAndDeletedFalse(assignStoreDto.getStaffId()).orElse(null);

        if (staff == null) {
            log.info("Staff not found");
            return new ResponseDto(400, "Staff not found");
        }

        StoreLocation storeLocation = storeLocationRepo.findByIdAndDeletedFalse(assignStoreDto.getStoreId())
                .orElse(null);

        if (storeLocation == null) {
            log.info("Store not found");
            return new ResponseDto(400, "Store not found");
        }

        serviceOrder.setStore(storeLocation);
        serviceOrder.setAssignedTo(staff);

        serviceOrderRepo.save(serviceOrder);

        log.info("Service : {} assigned to : {}", serviceOrder.getService().getServiceName(), staff.getFullName());

        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto deleteOrder(Long orderId) {
        log.info("Deleting order: >>>>>>>>{}", Username.getUsername());

        Order order = orderRepo.findByIdAndDeletedFalse(orderId).orElse(null);

        if (order == null) {
            log.info("Order not found");
            return new ResponseDto(400, "Order not found");
        }

        if (order.isPaid()) {
            log.info("Order already paid");
            return new ResponseDto(400, "Order already paid");
        }

        order.setDeleted(true);

        orderRepo.save(order);

        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto markOrderPaidManualy(PaymentDto paymentDto) {
        log.info("Marking order paid manually: >>>>>>>>{}", Username.getUsername());

        Order order = orderRepo.findByIdAndDeletedFalse(paymentDto.getOrderId()).orElse(null);

        if (order == null) {
            log.info("Order not found");
            return new ResponseDto(400, "Order not found");
        }

        if (order.isPaid()) {
            log.info("Order already paid");
            return new ResponseDto(400, "Order already paid");
        }

        // create payment Entity

        PaymentsEntity payment = paymentDto.toEntity(order);

        payment = paymentsRepo.save(payment);

        order.setPaid(true);
        order.setPayment(payment);

        log.info("Order : {} marked paid manually", order.getOrderNumber());

        orderRepo.save(order);

        // reduce the units in a store
        StoreInventory storeInventory = storeInventoryRepo.findByItemVariantAndStoreLocation(order.getItemVariant(),
                order.getAssignedStore());

        if (storeInventory != null) {
            int unitsLeft = storeInventory.getQuantity() - order.getUnits();
            storeInventory.setQuantity(unitsLeft);
            storeInventoryRepo.save(storeInventory);

            // if the left is negative send email
            if (unitsLeft < 0) {
                // send email
            }
        }

        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto markOrderDelivered(Long orderId) {
        log.info("Marking order delivered: >>>>>>>>{}", Username.getUsername());

        Order order = orderRepo.findByIdAndDeletedFalse(orderId).orElse(null);

        if (order == null) {
            log.info("Order not found");
            return new ResponseDto(400, "Order not found");
        }

        if (order.isDelivered()) {
            log.info("Order already delivered");
            return new ResponseDto(400, "Order already delivered");
        }

        order.setDelivered(true);

        orderRepo.save(order);

        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto markServiceCompleted(Long orderId) {
        log.info("Marking service completed: >>>>>>>>{}", Username.getUsername());

        ServiceOrderEntity serviceOrder = serviceOrderRepo.findByIdAndDeletedFalse(orderId).orElse(null);

        if (serviceOrder == null) {
            log.info("Service order not found");
            return new ResponseDto(400, "Service order not found");
        }

        if (serviceOrder.isCompleted()) {
            log.info("Service already completed");
            return new ResponseDto(400, "Service already completed");
        }

        serviceOrder.setCompleted(true);

        serviceOrderRepo.save(serviceOrder);

        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto markServicePaidManually(PaymentDto paymentDto) {
        log.info("Marking service paid manually: >>>>>>>>{}", Username.getUsername());

        ServiceOrderEntity serviceOrder = serviceOrderRepo.findByIdAndDeletedFalse(paymentDto.getOrderId())
                .orElse(null);

        if (serviceOrder == null) {
            log.info("Service order not found");
            return new ResponseDto(400, "Service order not found");
        }

        if (serviceOrder.isPaid()) {
            log.info("Service already paid");
            return new ResponseDto(400, "Service already paid");
        }

        // create payment Entity

        PaymentsEntity payment = paymentDto.toEntity(serviceOrder);

        payment = paymentsRepo.save(payment);

        serviceOrder.setPaid(true);
        serviceOrder.setPayment(payment);

        log.info("Service : {} marked paid manually", serviceOrder.getOrderNumber());

        serviceOrderRepo.save(serviceOrder);

        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto removeOrderPayment(RemovePaymentDto removePaymentDto) {
        log.info("Removing order payment: >>>>>>>>{}", Username.getUsername());

        Order order = orderRepo.findByIdAndDeletedFalse(removePaymentDto.getOrderId()).orElse(null);

        if (order == null) {
            log.info("Order not found");
            return new ResponseDto(400, "Order not found");
        }

        if (!order.isPaid()) {
            log.info("Order not paid");
            return new ResponseDto(400, "Order not paid");
        }

        PaymentsEntity paymentsEntity = order.getPayment();
        paymentsEntity.setReversalReason(removePaymentDto.getReason());
        paymentsEntity.setReversalDate(DateUtils.currentDate());
        paymentsEntity.setReversed(true);
        paymentsRepo.save(paymentsEntity);

        order.setPaid(false);

        orderRepo.save(order);

        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto removeServiceOrderPayment(RemovePaymentDto removePaymentDto) {
        log.info("Removing service order payment: >>>>>>>>{}", Username.getUsername());

        ServiceOrderEntity serviceOrder = serviceOrderRepo.findByIdAndDeletedFalse(removePaymentDto.getOrderId())
                .orElse(null);

        if (serviceOrder == null) {
            log.info("Service order not found");
            return new ResponseDto(400, "Service order not found");
        }

        if (!serviceOrder.isPaid()) {
            log.info("Service order not paid");
            return new ResponseDto(400, "Service order not paid");
        }

        PaymentsEntity paymentsEntity = serviceOrder.getPayment();
        paymentsEntity.setReversalReason(removePaymentDto.getReason());
        paymentsEntity.setReversalDate(DateUtils.currentDate());
        paymentsEntity.setReversed(true);
        paymentsRepo.save(paymentsEntity);

        serviceOrder.setPaid(false);

        serviceOrderRepo.save(serviceOrder);

        return new ResponseDto(200, "Success");
    }

}
