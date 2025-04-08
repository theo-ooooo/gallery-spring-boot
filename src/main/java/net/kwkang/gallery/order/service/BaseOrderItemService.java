package net.kwkang.gallery.order.service;

import lombok.RequiredArgsConstructor;
import net.kwkang.gallery.order.entity.OrderItem;
import net.kwkang.gallery.order.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseOrderItemService implements OrderItemService {
    private final OrderItemRepository orderItemRepository;

    @Override
    public List<OrderItem> findAll(Integer orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    @Override
    public void saveAll(List<OrderItem> orderItems) {
        orderItemRepository.saveAll(orderItems);
    }
}
