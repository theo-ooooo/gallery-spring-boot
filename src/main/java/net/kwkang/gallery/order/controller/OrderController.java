package net.kwkang.gallery.order.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import net.kwkang.gallery.account.helper.AccountHelper;
import net.kwkang.gallery.order.dto.OrderRead;
import net.kwkang.gallery.order.dto.OrderRequest;
import net.kwkang.gallery.order.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class OrderController {

    private final AccountHelper accountHelper;
    private final OrderService orderService;

    @GetMapping("/api/orders")
    public ResponseEntity<?> readAll(HttpServletRequest req, Pageable pageable) {

        Integer memberId = accountHelper.getMemberId(req);

        Page<OrderRead> orders = orderService.findAll(memberId, pageable);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/api/orders/{id}")
    public ResponseEntity<?> read(@PathVariable Integer id, HttpServletRequest req) {

        Integer memberId = accountHelper.getMemberId(req);
        OrderRead order = orderService.find(id, memberId);

        if(order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping("/api/orders")
    public ResponseEntity<?> add(HttpServletRequest req, @RequestBody OrderRequest orderReq) {
        Integer memberId = accountHelper.getMemberId(req);

        orderService.order(orderReq, memberId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
