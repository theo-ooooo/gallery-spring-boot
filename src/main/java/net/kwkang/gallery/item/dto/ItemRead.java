package net.kwkang.gallery.item.dto;


import com.fasterxml.jackson.core.JsonToken;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class ItemRead {
    private Integer id;
    private String name;
    private String imgPath;
    private Integer price;
    private Integer discountPer;


}
