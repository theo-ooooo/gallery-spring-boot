package net.kwkang.gallery.order.service;

import net.kwkang.gallery.order.dto.OrderRead;
import net.kwkang.gallery.order.dto.OrderRequest;

import java.util.List;

public interface OrderService {

    List<OrderRead> findAll(Integer memberId);

    OrderRead find(Integer id, Integer memberId);

    void order(OrderRequest orderReq, Integer memberId);
}
