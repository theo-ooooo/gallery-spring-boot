package net.kwkang.gallery.order.service;

import net.kwkang.gallery.order.dto.OrderRead;
import net.kwkang.gallery.order.dto.OrderRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OrderService {

    Page<OrderRead> findAll(Integer memberId, Pageable pageable);

    OrderRead find(Integer id, Integer memberId);

    void order(OrderRequest orderReq, Integer memberId);
}
