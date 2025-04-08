package net.kwkang.gallery.block.service;

import lombok.RequiredArgsConstructor;
import net.kwkang.gallery.block.entity.Block;
import net.kwkang.gallery.block.repository.BlockRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseBlockService implements BlockService {
    private final BlockRepository blockRepository;

    @Override
    public void add(String token) {
        blockRepository.save(new Block(token));
    }

    @Override
    public boolean has(String token) {
        return blockRepository.findByToken(token).isPresent();
    }
}
