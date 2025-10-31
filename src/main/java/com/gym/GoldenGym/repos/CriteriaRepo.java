package com.gym.GoldenGym.repos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.gym.GoldenGym.dtos.reqdtos.CartItemsReqDto;
import com.gym.GoldenGym.dtos.reqdtos.ItemFetch;
import com.gym.GoldenGym.dtos.reqdtos.OrdersReq;
import com.gym.GoldenGym.dtos.reqdtos.ServicesReq;
import com.gym.GoldenGym.dtos.reqdtos.UserReqDto;
import com.gym.GoldenGym.dtos.reqdtos.VariantRequestDto;
import com.gym.GoldenGym.entities.CartItemEntity;
import com.gym.GoldenGym.entities.Category;
import com.gym.GoldenGym.entities.Item;
import com.gym.GoldenGym.entities.ItemVariant;
import com.gym.GoldenGym.entities.Order;
import com.gym.GoldenGym.entities.ServiceEntity;
import com.gym.GoldenGym.entities.ServiceOrderEntity;
import com.gym.GoldenGym.entities.StoreLocation;
import com.gym.GoldenGym.entities.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CriteriaRepo {
    private final EntityManager em;

    public Page<ItemVariant> fetchItems(VariantRequestDto itemRequestDto) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ItemVariant> cq = cb.createQuery(ItemVariant.class);
        Root<ItemVariant> root = cq.from(ItemVariant.class);

        List<Predicate> predicates = this.variantsPredicates(itemRequestDto, cb, root);

        // count
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<ItemVariant> countRoot = countQuery.from(ItemVariant.class);
        List<Predicate> countPredicates = this.variantsPredicates(itemRequestDto, cb, countRoot);
        countQuery.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[0]));
        Long count = em.createQuery(countQuery).getSingleResult();

        // fetch
        cq.select(root).where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("id")));

        Pageable pageable = PageRequest.of(itemRequestDto.getPageNumber(), itemRequestDto.getPageSize());

        List<ItemVariant> items = em.createQuery(cq).setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();
        return new PageImpl<>(items, pageable, count);
    }

    private List<Predicate> variantsPredicates(VariantRequestDto itemRequestDto, CriteriaBuilder cb,
            Root<ItemVariant> root) {
        List<Predicate> predicates = new java.util.ArrayList<>();

        predicates.add(cb.equal(root.get("deleted"), false));

        Join<ItemVariant, Item> itemJoin = root.join("item");

        if (itemRequestDto.getCategoryId() != null) {
            Join<Item, Category> categoryJoin = itemJoin.join("category");
            predicates.add(cb.equal(categoryJoin.get("id"), itemRequestDto.getCategoryId()));
        }
        if (itemRequestDto.getMinPrice() != null && itemRequestDto.getMaxPrice() != null) {
            predicates.add(
                    cb.between(root.get("variantPrice"), itemRequestDto.getMinPrice(), itemRequestDto.getMaxPrice()));
        } else {
            if (itemRequestDto.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("variantPrice"), itemRequestDto.getMinPrice()));
            }
            if (itemRequestDto.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("variantPrice"), itemRequestDto.getMaxPrice()));
            }
        }
        if (itemRequestDto.getName() != null) {
            predicates.add(
                    cb.like(cb.lower(itemJoin.get("itemName")), "%" + itemRequestDto.getName().toLowerCase() + "%"));
        }
        return predicates;
    }

    public Page<Item> fetchItems(ItemFetch itemFetch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Item> cq = cb.createQuery(Item.class);
        Root<Item> root = cq.from(Item.class);

        List<Predicate> predicates = this.itemsPredicates(itemFetch, cb, root);

        // count
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Item> countRoot = countQuery.from(Item.class);
        List<Predicate> countPredicates = this.itemsPredicates(itemFetch, cb, countRoot);
        countQuery.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[0]));
        Long count = em.createQuery(countQuery).getSingleResult();

        // fetch
        cq.select(root).where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("id")));

        Pageable pageable = PageRequest.of(itemFetch.getPageNumber(), itemFetch.getPageSize());

        List<Item> items = em.createQuery(cq).setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();
        return new PageImpl<>(items, pageable, count);
    }

    private List<Predicate> itemsPredicates(ItemFetch itemFetch, CriteriaBuilder cb, Root<Item> root) {
        List<Predicate> predicates = new java.util.ArrayList<>();

        predicates.add(cb.equal(root.get("deleted"), false));
        if (itemFetch.getName() != null) {
            predicates.add(cb.like(cb.lower(root.get("itemName")), "%" + itemFetch.getName().toLowerCase() + "%"));
        }
        return predicates;
    }

    public Page<User> fetchUsers(UserReqDto userReqDto) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        List<Predicate> predicates = this.usersPredicates(userReqDto, cb, root);

        // count
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<User> countRoot = countQuery.from(User.class);
        List<Predicate> countPredicates = this.usersPredicates(userReqDto, cb, countRoot);
        countQuery.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[0]));
        Long count = em.createQuery(countQuery).getSingleResult();

        // fetch
        cq.select(root).where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("id")));

        Pageable pageable = PageRequest.of(userReqDto.getPageNumber(), userReqDto.getPageSize());

        List<User> users = em.createQuery(cq).setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();
        return new PageImpl<>(users, pageable, count);
    }

    private List<Predicate> usersPredicates(UserReqDto userReqDto, CriteriaBuilder cb, Root<User> root) {
        List<Predicate> predicates = new java.util.ArrayList<>();

        predicates.add(cb.equal(root.get("deleted"), false));

        // check store
        if (userReqDto.getStoreId() != null) {
            Join<User, StoreLocation> storeJoin = root.join("storeLocation");
            predicates.add(cb.equal(storeJoin.get("id"), userReqDto.getStoreId()));
        }

        // check email
        if (userReqDto.getEmail() != null) {
            predicates.add(cb.equal(cb.lower(root.get("email")), userReqDto.getEmail().toLowerCase()));
        }

        // check store name
        if (userReqDto.getStoreName() != null) {
            Join<User, StoreLocation> storeJoin = root.join("storeLocation");
            predicates.add(cb.equal(cb.lower(storeJoin.get("storeName")), userReqDto.getStoreName().toLowerCase()));
        }

        // check role
        if (userReqDto.getRole() != null) {
            predicates.add(cb.equal(root.get("role"), userReqDto.getRole()));
        }

        return predicates;
    }

    public Page<ServiceOrderEntity> fetchServiceOrders(ServicesReq servicesReq, Long storeId, Long clientId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ServiceOrderEntity> cq = cb.createQuery(ServiceOrderEntity.class);
        Root<ServiceOrderEntity> root = cq.from(ServiceOrderEntity.class);

        List<Predicate> predicates = this.serviceOrdersPredicates(servicesReq, storeId, clientId, cb, root);

        // count
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<ServiceOrderEntity> countRoot = countQuery.from(ServiceOrderEntity.class);
        List<Predicate> countPredicates = this.serviceOrdersPredicates(servicesReq, storeId, clientId, cb, countRoot);
        countQuery.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[0]));
        Long count = em.createQuery(countQuery).getSingleResult();

        // fetch
        cq.select(root).where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("id")));

        Pageable pageable = PageRequest.of(servicesReq.getPageNumber(), servicesReq.getPageSize());

        List<ServiceOrderEntity> orders = em.createQuery(cq)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();
        return new PageImpl<>(orders, pageable, count);
    }

    private List<Predicate> serviceOrdersPredicates(ServicesReq servicesReq, Long storeId, Long clientId,
            CriteriaBuilder cb,
            Root<ServiceOrderEntity> root) {
        List<Predicate> predicates = new java.util.ArrayList<>();

        predicates.add(cb.equal(root.get("deleted"), false));

        // check store
        if (storeId != null) {
            Join<ServiceOrderEntity, StoreLocation> storeJoin = root.join("store");
            predicates.add(cb.equal(storeJoin.get("id"), storeId));
        } else {
            if (servicesReq.getStoreName() != null) {
                Join<ServiceOrderEntity, StoreLocation> storeJoin = root.join("store");
                predicates
                        .add(cb.equal(cb.lower(storeJoin.get("storeName")), servicesReq.getStoreName().toLowerCase()));
            }
        }

        // check client email
        if (clientId != null) {
            Join<ServiceOrderEntity, User> clientJoin = root.join("client");
            predicates.add(cb.equal(clientJoin.get("id"), clientId));
        } else {
            if (servicesReq.getClientEmail() != null) {
                Join<ServiceOrderEntity, User> clientJoin = root.join("client");
                predicates
                        .add(cb.equal(cb.lower(clientJoin.get("email")), servicesReq.getClientEmail().toLowerCase()));
            }
        }

        // check order number
        if (servicesReq.getOrderNumber() != null) {
            predicates.add(cb.equal(root.get("orderNumber"), servicesReq.getOrderNumber()));
        }

        // check service name
        if (servicesReq.getServiceName() != null) {
            Join<ServiceOrderEntity, ServiceEntity> serviceJoin = root.join("service");
            predicates.add(
                    cb.equal(cb.lower(serviceJoin.get("serviceName")), servicesReq.getServiceName().toLowerCase()));
        }

        // check paid
        if (servicesReq.getPaid() != null) {
            predicates.add(cb.equal(root.get("paid"), servicesReq.getPaid()));
        }

        // check completed
        if (servicesReq.getCompleted() != null) {
            predicates.add(cb.equal(root.get("completed"), servicesReq.getCompleted()));
        }

        return predicates;
    }

    public Page<Order> fetchOrders(OrdersReq ordersReq, Long storeId, Long customerId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> root = cq.from(Order.class);

        List<Predicate> predicates = this.ordersPredicates(ordersReq, storeId, customerId, cb, root);

        // count
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Order> countRoot = countQuery.from(Order.class);
        List<Predicate> countPredicates = this.ordersPredicates(ordersReq, storeId, customerId, cb, countRoot);
        countQuery.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[0]));
        Long count = em.createQuery(countQuery).getSingleResult();

        // fetch
        cq.select(root).where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("id")));

        Pageable pageable = PageRequest.of(ordersReq.getPageNumber(), ordersReq.getPageSize());

        List<Order> orders = em.createQuery(cq).setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();
        return new PageImpl<>(orders, pageable, count);
    }

    private List<Predicate> ordersPredicates(OrdersReq ordersReq, Long storeId, Long customerId, CriteriaBuilder cb,
            Root<Order> root) {
        List<Predicate> predicates = new java.util.ArrayList<>();

        predicates.add(cb.equal(root.get("deleted"), false));

        // check store
        if (storeId != null) {
            Join<Order, StoreLocation> storeJoin = root.join("store");
            predicates.add(cb.equal(storeJoin.get("id"), storeId));
        } else {
            if (ordersReq.getStoreName() != null) {
                Join<Order, StoreLocation> storeJoin = root.join("store");
                predicates
                        .add(cb.equal(cb.lower(storeJoin.get("storeName")), ordersReq.getStoreName().toLowerCase()));
            }

        }

        // check client email
        if (customerId != null) {
            Join<Order, User> clientJoin = root.join("customer");
            predicates.add(cb.equal(clientJoin.get("id"), customerId));
        } else {
            if (ordersReq.getClientEmail() != null) {
                Join<Order, User> clientJoin = root.join("customer");
                predicates.add(cb.equal(cb.lower(clientJoin.get("email")), ordersReq.getClientEmail().toLowerCase()));
            }
        }

        // check order number
        if (ordersReq.getOrderNumber() != null) {
            predicates.add(cb.equal(root.get("orderNumber"), ordersReq.getOrderNumber()));
        }

        // check item name
        if (ordersReq.getItemName() != null) {
            Join<Order, Item> itemJoin = root.join("item");
            predicates.add(cb.equal(cb.lower(itemJoin.get("itemName")), ordersReq.getItemName().toLowerCase()));
        }

        // check paid
        if (ordersReq.getPaid() != null) {
            predicates.add(cb.equal(root.get("paid"), ordersReq.getPaid()));
        }

        // check delivered
        if (ordersReq.getDelivered() != null) {
            predicates.add(cb.equal(root.get("delivered"), ordersReq.getDelivered()));
        }

        return predicates;
    }

    public Page<CartItemEntity> fetchCartItems(CartItemsReqDto cartItemReq, Long userId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CartItemEntity> cq = cb.createQuery(CartItemEntity.class);
        Root<CartItemEntity> root = cq.from(CartItemEntity.class);

        List<Predicate> predicates = this.cartItemsPredicates(cartItemReq, userId, cb, root);

        // count
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<CartItemEntity> countRoot = countQuery.from(CartItemEntity.class);
        List<Predicate> countPredicates = this.cartItemsPredicates(cartItemReq, userId, cb, countRoot);
        countQuery.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[0]));
        Long count = em.createQuery(countQuery).getSingleResult();

        // fetch
        cq.select(root).where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("id")));

        Pageable pageable = PageRequest.of(cartItemReq.getPageNumber(), cartItemReq.getPageSize());

        List<CartItemEntity> cartItems = em.createQuery(cq)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();
        return new PageImpl<>(cartItems, pageable, count);
    }

    private List<Predicate> cartItemsPredicates(CartItemsReqDto cartItemReq, Long userId, CriteriaBuilder cb,
            Root<CartItemEntity> root) {
        List<Predicate> predicates = new java.util.ArrayList<>();

        predicates.add(cb.equal(root.get("deleted"), false));

        Join<CartItemEntity, User> clientJoin = root.join("client");
        predicates.add(cb.equal(clientJoin.get("id"), userId));

        // check item name
        if (cartItemReq.getItemName() != null) {
            Join<CartItemEntity, ItemVariant> variantJoin = root.join("itemVariant");
            Join<ItemVariant, Item> itemJoin = variantJoin.join("item");
            predicates.add(cb.equal(cb.lower(itemJoin.get("itemName")), cartItemReq.getItemName().toLowerCase()));
        }

        return predicates;
    }

}
