package net.kwkang.gallery.order.service;

import net.kwkang.gallery.order.entity.OrderItem;

import java.util.List;

public interface OrderItemService {

    List<OrderItem> findAll(Integer orderId);

    void saveAll(List<OrderItem> orderItems);
}
