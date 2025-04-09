package net.kwkang.gallery.order.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.kwkang.gallery.cart.service.CartService;
import net.kwkang.gallery.common.utill.EncryptionUtils;
import net.kwkang.gallery.item.dto.ItemRead;
import net.kwkang.gallery.item.service.ItemService;
import net.kwkang.gallery.order.dto.OrderRead;
import net.kwkang.gallery.order.dto.OrderRequest;
import net.kwkang.gallery.order.entity.Order;
import net.kwkang.gallery.order.entity.OrderItem;
import net.kwkang.gallery.order.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final ItemService itemService;
    private final CartService cartService;

    @Override
    public Page<OrderRead> findAll(Integer memberId, Pageable pageable) {
        Page<Order> orders = orderRepository.findAllByMemberIdOrderByIdDesc(memberId, pageable);
        return orders.map(Order::toRead);
    }

    @Override
    public OrderRead find(Integer id, Integer memberId) {
        Optional<Order> orderOptional = orderRepository.findByIdAndMemberId(id, memberId);

        // 값이 있으면?
        if(orderOptional.isPresent()) {
            OrderRead order = orderOptional.get().toRead();

            List<OrderItem> orderItems = orderItemService.findAll(order.getId());
            List<Integer> orderItemIds = orderItems.stream().map(OrderItem::getItemId).toList();
            List<ItemRead> items = itemService.findAll(orderItemIds);
            order.setItems(items);

            return order;
        }

        return null;
    }

    @Override
    @Transactional
    public void order(OrderRequest orderReq, Integer memberId) {
        List<ItemRead> items = itemService.findAll(orderReq.getItemIds());
        long amount = 0L;

        for (ItemRead item : items) {
            amount += item.getPrice() - item.getPrice().longValue() * item.getDiscountPer() / 100;
        }

        orderReq.setAmount(amount);

        // 결제수단이 카드일때 카드 번호 암호화
        if("card".equals(orderReq.getPayment())) {
            orderReq.setCardNumber(EncryptionUtils.encrypt(orderReq.getCardNumber()));
        }

        Order order = orderRepository.save(orderReq.toEntity(memberId));

        ArrayList<OrderItem> newOrderItems = new ArrayList<>();

        orderReq.getItemIds().forEach(itemId -> {
            OrderItem newOrderItem = new OrderItem(order.getId(), itemId);
            newOrderItems.add(newOrderItem);
        });

        orderItemService.saveAll(newOrderItems);

        cartService.removeAll(order.getMemberId());
    }
}
