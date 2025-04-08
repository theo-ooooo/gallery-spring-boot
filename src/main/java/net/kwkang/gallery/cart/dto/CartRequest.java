package net.kwkang.gallery.cart.dto;

import lombok.Getter;
import net.kwkang.gallery.cart.entity.Cart;

@Getter
public class CartRequest {
    private Integer itemId;

    public Cart toEntity(Integer memberId) {
        return new Cart(memberId, itemId);
    }
}
