package net.kwkang.gallery.item.service;

import lombok.RequiredArgsConstructor;
import net.kwkang.gallery.item.dto.ItemRead;
import net.kwkang.gallery.item.entity.Item;
import net.kwkang.gallery.item.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseItemService implements  ItemService {
    private final ItemRepository itemRepository;

    @Override
    public List<ItemRead> findAll() {
       return itemRepository.findAll().stream().map(Item::toRead).toList();
    }

    @Override
    public List<ItemRead> findAll(List<Integer> ids) {
        return itemRepository.findAllById(ids).stream().map(Item::toRead).toList();
    }
}
