package net.kwkang.gallery.cart.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import net.kwkang.gallery.account.helper.AccountHelper;
import net.kwkang.gallery.cart.dto.CartRead;
import net.kwkang.gallery.cart.dto.CartRequest;
import net.kwkang.gallery.cart.service.CartService;
import net.kwkang.gallery.item.dto.ItemRead;
import net.kwkang.gallery.item.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ItemService itemService;
    private final AccountHelper accountHelper;

    @GetMapping("/api/cart/items")
    public ResponseEntity<?> readAll(HttpServletRequest req) {

        Integer memberId = accountHelper.getMemberId(req);

        List<CartRead> carts = cartService.findAll(memberId);

        List<Integer> itemIds = carts.stream().map(CartRead::getItemId).toList();
        List<ItemRead> items = itemService.findAll(itemIds);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping("/api/carts")
    public ResponseEntity<?> push(HttpServletRequest req, @RequestBody CartRequest cartReq) {

        Integer memberId = accountHelper.getMemberId(req);

        CartRead cart = cartService.find(memberId, cartReq.getItemId());

        if(cart == null) {
            cartService.save(cartReq.toEntity(memberId));
        }

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/api/cart/items/{itemId}")
    public ResponseEntity<?> remove(HttpServletRequest req, @PathVariable Integer itemId) {
        Integer memberId = accountHelper.getMemberId(req);

        cartService.remove(memberId, itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
