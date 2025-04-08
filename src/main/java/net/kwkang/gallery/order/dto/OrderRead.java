package net.kwkang.gallery.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.kwkang.gallery.item.dto.ItemRead;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderRead {
    private Integer id;
    private String name;
    private String address;
    private String payment;
    private Long amount;
    private LocalDateTime created;
    private List<ItemRead> items;
}
