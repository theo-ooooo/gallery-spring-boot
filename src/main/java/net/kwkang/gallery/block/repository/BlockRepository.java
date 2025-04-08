package net.kwkang.gallery.block.repository;

import net.kwkang.gallery.block.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block, Integer> {

    Optional<Block> findByToken(String accessToken);
}
